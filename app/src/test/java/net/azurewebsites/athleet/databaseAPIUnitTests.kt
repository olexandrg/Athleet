package net.azurewebsites.athleet
//import com.squareup.moshi.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Converter
import org.junit.Test
import org.junit.Assert.*
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class databaseAPIUnitTests {
    //val client = OkHttpClient().newBuilder().build()
    val client = OkHttpClient().newBuilder().build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://localhost:44394/api/")
        .client(client).addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(AthleetService::class.java)

    val call = service.getUsers(1)
    val result = call.toString()
    @Test
    fun displayMessage() {
        print(result)
        assertEquals("SimiF", result)
    }
}




