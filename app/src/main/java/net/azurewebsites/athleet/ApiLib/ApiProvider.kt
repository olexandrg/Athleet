package net.azurewebsites.athleet.ApiLib

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
    fun getAllUsers(@Header("Authorization") token: String): Call<List<UserItem>>

    // delete user by user id NOT firebaseID
    @DELETE("Users/{userID}")
    fun deleteUserByName(@Header("Authorization") token: String, @Path("userID") userID: Int?): Call<ResponseBody>

    // add new user
    @POST("Users")
    fun addNewUser(@Header("Authorization") token: String, @Body user: UserItem): Call<UserItem>

    // get all workouts
    @GET("Workouts")
    fun getAllWorkouts(@Header("Authorization") token: String): Call<List<WorkoutItem>>

    // add new workout
    @POST("Workouts")
    fun addNewWorkout(@Header("Authorization") token: String, @Body workout: WorkoutItem): Call<ResponseBody>

    // delete workout by name
    // will not work if a workout is referenced in UserWorkouts (SQL FK constraint)
    @DELETE("Workouts/{workoutID}")
    fun deleteWorkoutByName(
        @Header("Authorization") token: String,
        @Path("workoutID") workoutID: Int?
    ): Call<ResponseBody>

    // get all user workouts
    @GET("UserWorkouts")
    fun getAllUserWorkouts(@Header("Authorization") token: String): Call<UserWorkout>

    // add new user workout
    @POST("UserWorkouts")
    fun addNewUserWorkout(
        @Header("Authorization") token: String,
        @Body userWorkout: UserWorkoutsItem
    ): Call<ResponseBody>

    // delete user workout
    @DELETE("UserWorkouts/{workoutId}")
    fun deleteUserWorkout(
        @Header("Authorization") token: String,
        @Path("workoutId") workoutId: Int?
    ): Call<ResponseBody>

    // view all exercises
    @GET("Exercises")
    fun getAllExercises(
        @Header("Authorization") token: String
    ) : Call<Exercises>

    // add new exercise
    @POST("Exercises")
    fun addNewExercise(
        @Header("Authorization") token: String,
        @Body exercise: ExercisesItem
    ): Call<ResponseBody>

    // delete exercise
    @DELETE("Exercises/{exerciseId}")
    fun deleteExercise(
        @Header("Authorization") token: String,
        @Path("exerciseId") exerciseId: Int?
    ): Call<ResponseBody>

    @DELETE("Team")
    fun deleteTeam(
        @Header("Authorization") token: String,
        @Query("teamName") teamName: String
    ): Call<ResponseBody>

    // get all workout exercises
    @GET("WorkoutExercises")
    fun getAllWorkoutExercises(
        @Header("Authorization") token: String
    ): Call<WorkoutExercises>

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
                    //      .addInterceptor(interceptor)
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