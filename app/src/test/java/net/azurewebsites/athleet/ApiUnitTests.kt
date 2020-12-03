package net.azurewebsites.athleet
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    //var client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun apiFactory(): Api {return Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDY5NTYzMjgsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNjk1NjMyOCwiZXhwIjoxNjA2OTU5OTI4LCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.iKlaJPCVfj5AgOSlPExWE2FwT19OPDl9qkqd3nGkRoC3yy6PqvYGeZOEQGGmW6iHXJFbzwR8TJzmHqG6BuMBQl4phy4-SqQ-S-mqSexS7XQMY2EioB6NWLYsqjJOSCk_xHaixoc_WkEEeft5nv5ECc_Fm7GJhu4L5X5ORa9dnFxf949x1WIxxmtgHAq2IXZUaiTArsOiaDtVLyXH8EgymrmpEq-LA6m_uHpAp6UagojhFqibzoe7Q6gt90jyQgJoQg8LRELNoi8J2LJ9vSxA9nuy1U3auBA8kK1lk7WwkF5UwtNBNBrIVh5wWtibKOTTSsEUuAb1fNsx21LoUusSdA"
        return "Bearer $firebaseToken"
    }


    @Test
    // returns 200 for successful GET from the Api
    // returns 401 if not successful; will need to replace token or check if the api service is up
    fun displaySuccessfulConnection(){
        val api = apiFactory()
        val response = api.getAllUsers(tokenFactory()).execute()
        assertEquals(response.code(), 200)
    }

    @Test
    // pulls user from the database by name
    fun displayUserName(){
        val api = apiFactory()
        val list = api.getAllUsers(tokenFactory()).execute().body()
        val user = "TestUser"
        val response = userHander.returnUserName(list, user)
        assertEquals(user, response)
    }

    @Test
    // returns error message that user does not exist
    fun displayUserException() {
        val api = apiFactory()
        val list = api.getAllUsers(tokenFactory()).execute().body()
        val user = "safasdf3254" //garbage value on purpose
        val response = userHander.returnUserName(list, user)
        assertEquals("User $user not found.", response)
    }
    @Test
    // returns user ID based on name passed
    fun displayUserID() {
        val api = apiFactory()
        val list = api.getAllUsers(tokenFactory()).execute().body()
        val user = "TestUser"
        val response = userHander.returnUserID(list, user)
        assertEquals("2", response)
    }
    @Test
    // fetches userID by name and deletes the user
    fun deleteUserByName() {
        val api = apiFactory()
        val list = api.getAllUsers(tokenFactory()).execute().body()
        val user = "TestUser"
        val userID = userHander.returnUserID(list, user)
        val response = api.deleteUserByName(tokenFactory(), userID).execute().code()
        assertEquals(200, response)

    }
    @Test
    fun addNewUser() {
        val api = apiFactory()
        val user = UserItem(firebaseUID = "dummy_id", userName = "TestPOSTUser", userHeadline = "Test POST headline", userId = 555)
        val apiResponse = api.addNewUser(tokenFactory(), user).execute()
        val apiTextResponse = apiResponse.body().toString()
        val list = api.getAllUsers(tokenFactory()).execute().body()
        val response = userHander.returnUserName(list, "TestPOSTUser")
        assertEquals("TestPOSTUser", apiTextResponse)

    }
}