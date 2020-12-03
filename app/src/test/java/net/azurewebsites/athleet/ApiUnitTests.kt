package net.azurewebsites.athleet
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    //var client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun apiFactory(): Api {return Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDY5NjA1MzgsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNjk2MDUzOCwiZXhwIjoxNjA2OTY0MTM4LCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.4FZlx49r98QUD1X-MnM-HptHjbNhNX0KH6MDEeR72C0DSPwHzR6d2HgGz2ZW7thurjUAxIkdyiik0u9cjt-pHiSQq519OIvXSbyzp1T2LWwZKqwSu5t67zlExg1ZqC-9O3y-TXTGKI7bVBunUeev3QTHUCHRuc1G2ycCldJUn1Qfb-kcXo890rBjicyY9WFHznwSkFNMo36WS5x1xRmGeXmErMwJ-HlBv1boe50xEk7MTtnq_hkcXVFeIfDcmOgLYvMFXCrsYh7fsxTuFOSxfwa8ffqpJXQ6aBbDop_jY4P1q6hmNCTSXqq3811UZ5-nY53xxOlzQrUU2HPvyfSqAg"
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
        val user = "SimiF"
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
    // adds new user and verifies response went through
    fun addNewUser() {
        val api = apiFactory()
        val user = UserItem(
                firebaseUID = "dummyid",
                userName = "TestAddUser",
                userHeadline = "Test Add headline",
                userId = null
                )
        val response = api.addNewUser(tokenFactory(), user).execute()
        val responseCode = response.code()
        assertEquals(201, responseCode)
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