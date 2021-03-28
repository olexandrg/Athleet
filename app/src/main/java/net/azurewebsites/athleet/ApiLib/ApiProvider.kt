package net.azurewebsites.athleet.ApiLib

import net.azurewebsites.athleet.Teams.Team
import net.azurewebsites.athleet.models.Exercise
import net.azurewebsites.athleet.models.TeamInfo
import net.azurewebsites.athleet.workouts.Workout
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


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
    @DELETE("Team/leave")
    fun leaveTeam(
        @Header("Authorization") token: String,
        @Query("userName") userName: String,
        @Query("UID") UID: String,
        @Query("description") description: String
    )

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