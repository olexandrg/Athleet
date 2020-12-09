package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    fun apiFactory(): Api {return Api.createSafe("https://testapi.athleetapi.club/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDc0NzcyMjIsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNzQ3NzIyMiwiZXhwIjoxNjA3NDgwODIyLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.S8CrNihaWJgwe_WBiTJr6xp1D4y5s5YFF8_qtSYspYq74mAT-aJzOKK84x6C4_k3PgtTghJ6Rq2P7QOmOl_ZEyOVz6D_rY4lXFKbU8Epm-nYYl0k3LOYMNAmG4QVicQdSYgAPeHolHMKJ5d6F3_xHKEqBdskQF8kIDaqr6UeeLGnfFiZ30f33uBZlY_xzOhV7K1zcuI8EDRqV3KB2ksUL64OIqgdiY9TDz0GpwqQShGvN9i4Yr2_24K9u3TIrrwYtihkiAoI60QTTlOY_J-5woQubqcADrmi4O5cXInbzyA6taE-nhbZH3RQYyXHFDFxd7LVX4HyGUrV-6v_ygG2ag"
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

    @Test // tests API response
    fun viewUserWorkoutsByFirebaseUID() {
        val api = apiFactory()
        val response = api.viewUserWorkoutsByUser(tokenFactory()).execute()

        run {
            assertEquals("GET /ViewUserWorkouts", 200, response.code())
        }

        run {
            assertEquals("Verify workout Name: ","Test Workout" , response.body()?.get(0)?.workoutName)
        }

        run {
            assertEquals("Verify workout Description: ","Test Description" , response.body()?.get(0)?.description)
        }
    }
}