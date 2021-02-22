package net.azurewebsites.athleet
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import net.azurewebsites.athleet.ApiLib.*
import net.azurewebsites.athleet.network.AthleetApi
import net.azurewebsites.athleet.network.Exercise
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {

    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    private fun apiFactory(): Api {return Api.createSafe()}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    // GET Api response tests
    // returns 200 for successful GET from the Api
    // returns 401 if not successful; will need to replace token or check if the api service is up

    @Test // tests API response
    fun displayAllWorkouts() {
        val api = apiFactory()
        val response = api.getUserWorkouts(tokenFactory()).execute()
        val responseCode = response.code()
        assertEquals("GET /api/Workouts ",200, responseCode )
    }

    @Test // tests API response
    fun getAllExercises() {
        val api = apiFactory()
        val response = api.getAllExercises(tokenFactory()).execute().code()
        assertEquals("GET /api/exercises",200, response)
    }

    @Test // tests API response
    fun getAllWorkoutExercises() {
        val api = apiFactory()
        val response =  api.getAllWorkoutExercises(tokenFactory()).execute().code()
        assertEquals("GET /api/WorkoutExercises ", 200, response)
    }

    @Test // Wil break if test user's exercises gets cleared :(
    fun getExercisesForWorkoutById_shouldMatchFirstExercise() {

        // Given
        val expected = Exercise(2, "Test Exercise", "Test Exercise Description", "5")

        // When
        testScope.launch {
            val response = AthleetApi.retrofitService.getExercisesForWorkout(tokenFactory(), "6")

            // Then
            assertEquals("GET /api/Exercises/workout/{workoutID}", expected, response[0])
        }
    }
}