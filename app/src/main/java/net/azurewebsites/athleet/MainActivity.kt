package net.azurewebsites.athleet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}

object ApiProvider {

    private fun getHttpClient() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiService.BASE_URL)
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <S> createService(serviceClass: Class<S>?) : S {
        return retrofit.create(serviceClass)
    }
}

interface ApiService {

    companion object {
        val BASE_URL: String = "https://localhost:44394/api/"
    }

    @GET("Users")
    suspend fun getResult(
        @Query("q") query: String?): Response<SearchResultModel?>

}
data class SearchResultModel(
    val UserId: Int?,
    val UserName: String?,
    val UserHeadline: String?,
    val FirebaseUID: String?
) {}

