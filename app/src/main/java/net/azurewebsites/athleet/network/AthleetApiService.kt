package net.azurewebsites.athleet.network

//import com.squareup.moshi.Moshi
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.http.GET
//import retrofit2.http.Header
//import retrofit2.http.Path
//
//// production
//private const val BASE_URL = "https://testapi.athleetapi.club/api/"
//
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create())
//    .baseUrl(BASE_URL)
//    .build()
//
//interface AthleetApiService {
//
//    @GET("exercises/workout/{workoutID}")
//    suspend fun getExercisesForWorkout(
//        @Header("Authorization") token: String,
//        @Path("workoutID") workoutID: String):
//            List<Exercise>
//
//}
//
//object AthleetApi {
//    val retrofitService : AthleetApiService by lazy {
//        retrofit.create(AthleetApiService::class.java)
//    }
//}