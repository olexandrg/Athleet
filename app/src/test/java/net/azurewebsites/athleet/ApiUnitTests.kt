package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    private fun apiFactory(): Api {return Api.createSafe("https://testapi.athleetapi.club/api/")}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    // GET Api response tests
    // returns 200 for successful GET from the Api
    // returns 401 if not successful; will need to replace token or check if the api service is up

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