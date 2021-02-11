package net.azurewebsites.athleet.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

// production
private const val BASE_URL = "https://testapi.athleetapi.club/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AthleetApiService {

//    @GET("Exercises/workout/35")
//    fun getExercisesForWorkout(@Header("Authorization") token: String):
//            Call<String>

    @GET("Workouts")
    fun getExercisesForWorkout(@Header("Authorization") token: String):
            Call<String>

}

object AthleetApi {
    val retrofitService : AthleetApiService by lazy {
        retrofit.create(AthleetApiService::class.java)
    }
}