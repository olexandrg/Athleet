package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    fun apiFactory(): Api {return Api.createSafe("https://testapi.athleetapi.club/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDc0NjQ3NjMsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNzQ2NDc2MywiZXhwIjoxNjA3NDY4MzYzLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.QJYU23ScT9P3FmfiYvAKqDe_prM9bFhuzGvCxAmOJR_6tWhFu8wLnvWTw1U9VG9tzWFPcydUVBLdz5rjeVAoP9-2HEIQwH-p7K1cYa04gwUC08yyy9Xi5jmLcGjTTkPTcaZlabxNjU7KsZ1rgCaDRavK1cMluc4zfA09AWHRZcNiRH3sj1ZrcxH9cbo4jfh8rsIJiLBZl9NXnAHomn63JeSJGzeyubdCzr0juWiXHZ0I9KaxvZ_NnKCMbA-kTVA6rNOiW-c8eEiW0bxMn18lW37yvPM5REJ29lnJyRfi083-7CBs9iy6NpFR_oCI3eMCMY8Vs5kVG0vw2oLgUGzxRA"
        return "Bearer $firebaseToken"
    }

    // GET Api response tests
    // returns 200 for successful GET from the Api
    // returns 401 if not successful; will need to replace token or check if the api service is up

    @Test // tests API response

    fun displayAllUsers(){
        val api = apiFactory()
        val response = api.getAllUsers(tokenFactory()).execute()
        assertEquals("GET api/Users", response.code(), 200)
    }

    @Test // tests API response
    fun displayUserException() {
        val api = apiFactory()
        val list = api.getAllUsers(tokenFactory()).execute().body()
        val user = "safasdf3254" //garbage value on purpose
        val response = userHandler.returnUserName(list, user)
        assertEquals("User $user not found.", response)
    }

    @Test // tests API response
    fun displayAllWorkouts() {
        val api = apiFactory()
        val response = api.getAllWorkouts(tokenFactory()).execute()
        val responseCode = response.code()
        assertEquals("GET /api/Workouts ",200, responseCode )
    }

    @Test // tests API response
    fun getUserWorkoutsList() {
        val api = apiFactory()
        val responseCode = api.getAllUserWorkouts(tokenFactory()).execute().code()
        assertEquals("GET /api/UserWorkouts ",200, responseCode )
    }

    @Test // tests API response
    fun getAllExercises() {
        val api = apiFactory()
        val response = api.getAllExercises(tokenFactory()).execute().code()
        assertEquals("GET /api/Exercises",200, response)
    }

    @Test // tests API response
    fun getAllWorkoutExercises() {
        val api = apiFactory()
        val response =  api.getAllWorkoutExercises(tokenFactory()).execute().code()
        assertEquals("GET /api/WorkoutExercises ", 200, response)
    }
}