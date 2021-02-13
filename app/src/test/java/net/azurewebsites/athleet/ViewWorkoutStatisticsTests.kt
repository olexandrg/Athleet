package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*

class ViewWorkoutStatisticsTests {
    private fun apiFactory(): Api {return Api.createSafe()}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    @Test
    fun searchUserWorkout() {
        val api = apiFactory()
        val workoutName = "Test Workout"
        val workoutDescription = "Test Description"

        val searchWorkouts = api.viewUserWorkoutsByUser(tokenFactory()).execute()

        run {
            assertEquals("GET /ViewUserWorkouts", 200, searchWorkouts.code())
        }

        run {
            assertEquals("Verify workout Name: ",workoutName , searchWorkouts.body()?.get(0)?.workoutName)
        }

        run {
            assertEquals("Verify workout Description: ",workoutDescription, searchWorkouts.body()?.get(0)?.description)
        }
    }
}