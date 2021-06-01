package net.azurewebsites.athleet.ApiLib

import net.azurewebsites.athleet.models.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*


interface Api {
    @GET("Users")
    fun checkExistingUser(
        @Header("Authorization") token: String
    ):Call<ResponseBody>

//  ############################################
//  ############## USER STUFF ##################
//  ############################################

    @GET("Users")
    fun retrieveExistingUser(
        @Header("Authorization") token: String
    ):Call<List<UserItem>>

    @PUT("Users/{id}")
    fun updateExistingUser(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body user: UserItem
    ):Call<ResponseBody>

    // insert new user
    @POST("Users")
    fun addNewUser(
        @Header("Authorization") token: String,
        @Query("userName") userName:String,
        @Query("description") description: String
    ): Call<ResponseBody>

    @GET("Users/BlockedUsers")
    fun retrieveBlockedUsers(
        @Header("Authorization") token: String
    ):Call<List<String>>

    @POST("Users/blockUser")
    fun blockUser(
        @Header("Authorization")token:String,
        @Query("UserName") username:String
    ):Call<ResponseBody>

    @POST("Users/unblockUser")
    fun unblockUser(
        @Header("Authorization")token:String,
        @Query("UserName") username:String
    ):Call<ResponseBody>

//  ############################################
//  ############## WORKOUT STUFF ###############
//  ############################################

    @POST("Workouts")
    fun addNewWorkout(
        @Header("Authorization")token:String,
        @Query("Name") name:String,
        @Query("Description") description: String
    ):Call<ResponseBody>

    @GET("Workouts")
    fun getWorkout(
        @Header("Authorization") token: String
    ) : Call<List<Workout>>

    // delete and existing workout
    // only returns a status code 201 on successful completion
    @GET("Workouts/DeleteWorkout")
    fun deleteWorkoutByUser(
        @Header("Authorization") token: String,
        @Query("Name") Name: String
    ): Call<ResponseBody>

//  ############################################
//  ############## EXERCISE STUFF ##############
//  ############################################

    @POST("Exercises")
    fun addNewExercise(
        @Header("Authorization")token:String,
        @Query("Name") name:String,
        @Query("Description") description: String,
        @Query("DefaultReps") defaultReps:Int,
        @Query("ExerciseSets") exerciseSets: Int,
        @Query("MeasureUnits") measureUnits:String,
        @Query("unitCount") unitCount: Float,
        @Query("WorkoutID") workoutID: Int
    ):Call<ResponseBody>

    @GET("exercises/workout/{workoutID}")
    suspend fun getExercisesForWorkout(
        @Header("Authorization") token: String,
        @Path("workoutID") workoutID: String):
            List<Exercise>

    @GET("exercises/{exerciseId}")
    suspend fun getExercise(
        @Header("Authorization") token: String,
        @Path("exerciseId") exerciseId: Int
    ):Exercise

    @PUT("exercises/{exerciseId}")
    fun updateExercise(
        @Header("Authorization") token: String,
        @Path("exerciseId") exerciseId: Int,
        @Body exercise: Exercise
    ):Call<ResponseBody>

//  ############################################
//  ############## TEAMS STUFF #################
//  ############################################

    // delete a whole team
    @DELETE("Team")
    fun deleteTeam(
        @Header("Authorization") token: String,
        @Query("teamName") teamName: String
    ): Call<ResponseBody>


    @GET("Team")
    fun teamInfo(
        @Header("Authorization") token: String,
        @Query("teamName") teamName: String
    ): Call<TeamInfo>

    // leave a team
    @GET("Team/leave")
    fun leaveTeam(
        @Header("Authorization") token: String,
        @Query("teamName") teamName: String
    ): Call<ResponseBody>

//  ############################################
//  ############### CHAT STUFF #################
//  ############################################

    //get team conversation
    @GET("Chat/team")
    fun getTeamConversation(
        @Header("Authorization") token: String,
        @Query("teamName") teamName: String
    ): Call<List<Conversation>>

    //post team message
    @POST("Chat")
    fun saveTeamMessage(
        @Header("Authorization") token: String,
        @Query("conversationID") conversationID: Int,
        @Query("content") content: String
    ): Call<ResponseBody>

//  #####################################################################
//  ############## FACTORY METHOD FOR INSTANTIATING API #################
//  #########################  NO TOUCH  ################################
//  #####################################################################

    // Update admin status of a team
    // returns 200 upon proper execution of server-side procedure of updating userName within teamName to admin
    @PUT("Team/{teamName}/{userName}")
    fun makeTeamUserCoach(
        @Header("Authorization") token: String,
        @Path("teamName") teamName: String,
        @Path("userName") userName: String,
        @Query("isAdmin") isAdmin: Boolean
    ): Call<ResponseBody>

    @PUT("Team/invite/{teamName}/{userName}")
    fun inviteExistingUserToTeam(
        @Header("Authorization") token: String,
        @Path("teamName") teamName: String,
        @Path("userName") userName: String
    ) : Call<ResponseBody>

    @POST("Team")
    fun createTeam(
        @Header("Authorization") token: String,
        @Query("teamName") teamName: String,
        @Query("description") description: String
    ): Call<ResponseBody>

    @GET("Team/list")
    fun listTeams(
        @Header("Authorization") token: String
    ): Call<List<Team>>

    // Delete a message which was warned about
    @DELETE("Chat/delete")
    fun deleteMessage(
        @Header("Authorization") token: String,
        @Query("userName") userName: String,
        @Query("message") messageToBeDeleted: String
    ): Call<ResponseBody>

    // Sending a warning message to the user
    @POST("Team/warn")
    fun warnUser(
        @Header("Authorization") token: String,
        @Query("Date") date: Date
    ) : Call<ResponseBody>

    // Getting the warning logs for the user
    @GET("Team/warning")
    fun getWarnings(
        @Header("Authorization") token: String
    ) : Call<Warning>

    // factory method
    companion object {
        fun createSafe(baseUrl: String = "https://testapi.athleetapi.club/api/"): Api {
            // add interceptor to read raw JSON
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            // create retrofit client
            val retrofit = Retrofit.Builder()
                    // here we set the base url of our API
                    .baseUrl(baseUrl)
                    // make OkHttpClient instance
                    .client(OkHttpClient().newBuilder()
                    // add raw response interceptor
                          .addInterceptor(interceptor)
                        .build())
                    // add the JSON dependency so we can handle json APIs
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            // here we pass a reference to our API interface
            // and get back a concrete instance
            return retrofit.create(Api::class.java)
        }
    }
}