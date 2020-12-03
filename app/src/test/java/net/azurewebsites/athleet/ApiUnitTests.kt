package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    //var client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun apiFactory(): Api {return Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDY5NjYxMDYsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNjk2NjEwNiwiZXhwIjoxNjA2OTY5NzA2LCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.uyCfjPTF3358pDeBKuGTBX26p3xM3lbAHHiAUFa36SvCYLUWvLjOtxPglEFrLXp5zm8x8KXo5Bv_Lz0JHB5znoZ7s4Qr1VF2zN8kJopWot1kmQWaSSZx1RLyfCEdKzNWwsrmCr58DFAZvVBmSEvxmfQW_ZqAGOABGWy-BdtMkBE5HX9__BSAvbw97VO_zai_mOUEj8SYIyOBNtBWTXpf1fy3zwT9ezLeiV1BY-3zOGbIOF3s9pgfJAgVZGih29W94MP9EVdtZv_wZxYNeuu0FS3wt4Ql6S1hcKRrEZIYoqHSP2qE_r_Qxn5awJkWEK2piq1T3KU_Q3TIp3c2_GhpgA"
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
    fun testUser() {
        //declare var's and val's used throughout the whole method
        val userName = "TestUser"
        var userId = ""
        val api = apiFactory()

        //add user
        run {
            val user = UserItem(
                    firebaseUID = "dummyid2",
                    userName = userName,
                    userHeadline = "Test Add headline",
                    userId = null
            )
            val cResponse = api.addNewUser(tokenFactory(), user).execute()
            val responseCode = cResponse.code()
            userId = cResponse.body()?.userId.toString()
            assertEquals(201, responseCode)
        }


        //check userName
        run {
            val list = api.getAllUsers(tokenFactory()).execute().body()
            val unResponse = userHander.returnUserName(list, userName)
            assertEquals(userName, unResponse)
        }

        //check userId
        run {
            val list = api.getAllUsers(tokenFactory()).execute().body()
            val response = userHander.returnUserID(list, userName)
            assertEquals(userId, response)
        }

        //delete user
        val dList = api.getAllUsers(tokenFactory()).execute().body()
        val userID = userHander.returnUserID(dList, userName)
        val dResponse = api.deleteUserByName(tokenFactory(), userID).execute().code()
        assertEquals(200, dResponse)
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
    // get all workouts
    fun viewAllWorkouts() {
        val api = apiFactory()
        val response = api.getAllWorkouts(tokenFactory()).execute()
        val responseCode = response.code()
        assertEquals(200, responseCode )
    }

    @Test
    // adds new workout
    fun addNewWorkout() {
        val api = apiFactory()
        val workout = WorkoutItem(
                workoutId = null,
                workoutName = "Chest",
                description = "Chest day workout"
        )
        val response = api.addNewWorkout(tokenFactory(), workout).execute()
        val responseCode = response.code()
        assertEquals(201, responseCode)
    }

    @Test
    // deletes workout by name
    fun deleteWorkoutByName() {
        val api = apiFactory()
        val list = api.getAllWorkouts(tokenFactory()).execute().body()
        val workout = "Chest"
        val workoutID = workoutHandler.returnWorkoutID(list, workout)
        val response = api.deleteWorkoutByName(tokenFactory(), workoutID).execute()
        val responseCode = response.code()
        assertEquals(200, responseCode)
    }
}