package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    fun apiFactory(): Api {return Api.createSafe("http://athleetapi-dev.us-west-2.elasticbeanstalk.com/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDczODE1MzcsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNzM4MTUzNywiZXhwIjoxNjA3Mzg1MTM3LCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.FaCOdF-NOdcegBU27y1pOUuglFHNCIv0Us5KTEVgPtTWXfquvVktjVnoexenm2pcvUVFkLZrv8vFMtXtApCAPAKUWK-aONh6iGdpdvZ_Ow1BIXYPvJPOgIe_JxN_KDwUn_Vy-cIxmzUTmnIQgW0P5ub24_QBpKK4UjiDwpuQlUjjhWP23_-PDYFpKtA_8j_kqEEPAqw6mGiYwLOwzo1HFoPxEpX5oOwuhvSoVuQaTXBaMIwTtk-VjwQKLjKccR0kjkG6R5vt0rZcsRHpu60sPMwxYApl5PkoOYVk41STQBY7v0CHzj5nTb8LOU9G3NWFoqL7R2ytLvy2EU8oZq9OMA"
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