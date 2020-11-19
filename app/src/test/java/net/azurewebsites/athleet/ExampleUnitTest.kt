package net.azurewebsites.athleet
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {
    //var client = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    @Test
    fun displayMessage(){
        val api = Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")
        val response = api.getUsers().execute()
        assertEquals(response.code(), 200)
    }

    @Test
    fun displayUser(){
        val api = Api.createSafe("https://localhost:44394/api/")
        val response = api.getUsers().execute()
        val user = api.getUsers().execute().body()?.get(0)?.toString()
        assertEquals("SimiF", user)
    }
}