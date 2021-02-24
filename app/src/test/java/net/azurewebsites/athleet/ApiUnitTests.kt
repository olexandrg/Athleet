package net.azurewebsites.athleet
import android.util.Log
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*
import java.nio.charset.Charset


class ApiUnitTests {
    private fun apiFactory(): Api {return Api.createSafe()}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }
    @Test
    fun checkExistingUser() {
        val api = apiFactory()
        val response = api.checkExistingUser(tokenFactory()).execute().body()?.source()?.readString(charset = Charset.defaultCharset())
        val userData = response?.split("[", "]", "{", "}", ",",":")?.map { it.trim()}

        println(response)
        println(userData?.get(5))
        println(userData?.get(9))
    }
    // GET Api response tests
    // returns 200 for successful GET from the Api
    // returns 401 if not successful; will need to replace token or check if the api service is up

//    @Test // tests API response
//    fun displayAllWorkouts() {
//        val api = apiFactory()
//        val response = api.getUserWorkouts(tokenFactory()).execute()
//        val responseCode = response.code()
//        assertEquals("GET /api/Workouts ",200, responseCode )
//    }
//
//    @Test // tests API response
//    fun getAllExercises() {
//        val api = apiFactory()
//        val response = api.getAllExercises(tokenFactory()).execute().code()
//        assertEquals("GET /api/exercises",200, response)
//    }
//
//    @Test // tests API response
//    fun getAllWorkoutExercises() {
//        val api = apiFactory()
//        val response =  api.getAllWorkoutExercises(tokenFactory()).execute().code()
//        assertEquals("GET /api/WorkoutExercises ", 200, response)
//    }
}