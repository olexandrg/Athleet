package net.azurewebsites.athleet.ApiLib

import net.azurewebsites.athleet.models.Exercise
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
    // insert new user
    @POST("Users")
    fun addNewUser(
        @Header("Authorization") token: String,
        @Query("userName") userName:String,
        @Query("description") description: String
    ): Call<ResponseBody>

    @POST("Workouts")
    fun addNewWorkout(
        @Header("Authorization")token:String,
        @Query("Name") name:String,
        @Query("Description") description: String
    ):Call<ResponseBody>
    // get all user workouts
    @GET("UserWorkouts")
    fun getUserWorkouts(
        @Header("Authorization") token: String
    ): Call<List<UserWorkouts>>

    @GET("Workouts")
    fun getWorkout(
        @Header("Authorization") token: String,
        @Query("workoutID") workoutID:String
    ) : Call<Workout>

    @GET("Workouts")
    fun getWorkout(
        @Header("Authorization") token: String
    ) : Call<List<Workout>>

    // view all exercises
    @GET("exercises")
    fun getAllExercises(
        @Header("Authorization") token: String
    ) : Call<Exercises>

    // get all workout exercises
    @GET("WorkoutExercises")
    fun getAllWorkoutExercises(
        @Header("Authorization") token: String
    ): Call<WorkoutExercises>

    // insert a new workout
    // only returns a status code 201 on successful completion
    @GET("Workouts/InsertWorkout")
    fun insertNewWorkoutByUser(
        @Header("Authorization") token: String,
        @Query("Name") Name: String,
        @Query("Description") Description: String?
    ): Call<ResponseBody>

    // delete and existing workout
    // only returns a status code 201 on successful completion
    @GET("Workouts/DeleteWorkout")
    fun deleteWorkoutByUser(
        @Header("Authorization") token: String,
        @Query("Name") Name: String
    ): Call<ResponseBody>

    // view workouts procedure
    @GET("ViewUserWorkouts")
    fun viewUserWorkoutsByUser(
        @Header("Authorization") token: String
    ): Call<ViewUserWorkouts>

    // view exercises within a workout
    @GET("ViewExerciseInWorkout")
    fun viewExerciseInWorkoutByUser(
        @Header("Authorization") token: String,
        @Query("WorkoutName") WorkoutName: String?
    ): Call<ViewExercisesInWorkout>

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

    @GET("exercises/workout/{workoutID}")
    suspend fun getExercisesForWorkout(
        @Header("Authorization") token: String,
        @Path("workoutID") workoutID: String):
            List<Exercise>

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