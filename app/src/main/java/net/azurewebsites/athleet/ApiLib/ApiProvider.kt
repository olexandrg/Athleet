package net.azurewebsites.athleet.ApiLib

import com.google.firebase.auth.ActionCodeResult
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface Api {
    // fetch a list of all users
    @GET("Users")
    fun getAllUsers(
        @Header("Authorization") token: String
    ): Call<List<UserItem>>

   //  delete user by user id NOT firebaseID
    @DELETE("Users/{userID}")
    fun deleteUserByName(
        @Header("Authorization") token: String,
        @Path("userID") userID: Int?
    ): Call<ResponseBody>

    // insert new user
    @POST("Users")
    fun addNewUser(
        @Header("Authorization") token: String,
        @Query("userName") userName:String,
        @Query("description") description: String
    ): Call<ResponseBody>

    // get all workouts
    @GET("Workouts")
    fun getAllWorkouts(
        @Header("Authorization") token: String
    ): Call<List<WorkoutItem>>

    // get all user workouts
    @GET("UserWorkouts")
    fun getAllUserWorkouts(
        @Header("Authorization") token: String
    ): Call<UserWorkout>

    // view all exercises
    @GET("Exercises")
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

    @GET("Users/CreateUser")
    fun addNewUser(
        @Header("Authorization") token: String,
        @Query("userName") userName: String,
        @Query("UID") UID: String,
        @Query("description") description: String
    )

    // factory method
    companion object {
        fun createSafe(baseUrl: String): Api {
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