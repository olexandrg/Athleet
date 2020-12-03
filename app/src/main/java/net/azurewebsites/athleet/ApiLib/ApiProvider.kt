package net.azurewebsites.athleet.ApiLib

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.Header
import java.security.cert.CertificateException
import javax.net.ssl.*

interface Api {
    // fetch a list of all users
    @GET("Users")
    fun getAllUsers(@Header("Authorization") token: String): Call<List<UserItem>>

    // delete user by user id NOT firebaseID
    @DELETE("Users/{userID}")
    fun deleteUserByName(@Header("Authorization") token: String, @Path("userID") userID: String): Call<ResponseBody>

    // add new user
    @POST("Users")
    fun addNewUser(@Header("Authorization") token: String, @Body user: UserItem): Call <UserItem>

    // get all workouts
    @GET("Workouts")
    fun getAllWorkouts(@Header("Authorization") token: String): Call<List<WorkoutItem>>

    // add new workout
    @POST("Workouts")
    fun addNewWorkout(@Header("Authorization") token: String, @Body workout: WorkoutItem): Call<ResponseBody>

    // delete workout by name
    @DELETE("Workouts/{workoutID}")
    fun deleteWorkoutByName(@Header("Authorization") token: String, @Path("workoutID") workoutID: String): Call<ResponseBody>

    // factory method
    companion object {
        fun createSafe(baseUrl: String): Api {
            val retrofit = Retrofit.Builder()
                    // here we set the base url of our API
                    .baseUrl(baseUrl)
                    // make OkHttpClient instance
                    .client(OkHttpClient().newBuilder().build())
                    // add the JSON dependency so we can handle json APIs
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            // here we pass a reference to our API interface
            // and get back a concrete instance
            return retrofit.create(Api::class.java)
        }


        fun createUnsafe(baseUrl: String): Api {
            val client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
            val retrofit = Retrofit.Builder()
                    // here we set the base url of our API
                    .baseUrl(baseUrl)
                    // make OkHttpClient instance
                    .client(client)
                    // add the JSON dependency so we can handle json APIs
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            // here we pass a reference to our API interface
            // and get back a concrete instance
            return retrofit.create(Api::class.java)
        }
    }
}
class UnsafeOkHttpClient {
    companion object {
        fun getUnsafeOkHttpClient(): OkHttpClient {
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                // builder.hostnameVerifier { _, _ -> true }
                builder.hostnameVerifier(hostnameVerifier = HostnameVerifier { _, _ -> true })

                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}