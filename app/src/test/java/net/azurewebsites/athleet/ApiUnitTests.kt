package net.azurewebsites.athleet
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    //var client = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    @Test
    fun displayMessage(){
        val api = Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")
        val response = api.getAllUsers().execute()
        assertEquals(response.code(), 200)
    }

    @Test
    fun displayUser(){
        val api = Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")
        val user = api.getUserByName("SimiF").execute().body()?.get(0)?.userName.toString()
        assertEquals("SimiF", user)
    }
}