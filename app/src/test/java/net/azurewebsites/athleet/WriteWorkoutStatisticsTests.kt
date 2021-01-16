package net.azurewebsites.athleet

import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*

class WriteWorkoutStatisticsTests {
    private fun apiFactory(): Api {return Api.createSafe("https://testapi.athleetapi.club/api/")}
    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    @Test // returns 201 on successful insert
    fun insertThenDeleteNewWorkout() {
        val api = apiFactory()
        val workoutName = "Test Name Insert"
        val workoutDescription = "Test Desc Insert"

        val response = api.insertNewWorkoutByUser(tokenFactory(), workoutName, workoutDescription).execute()
        val searchWorkouts = api.viewUserWorkoutsByUser(tokenFactory()).execute()

        // verify insert worked
        run {
            assertEquals(201, response.code())
        }

        run {
            assertEquals("GET /ViewUserWorkouts", 200, searchWorkouts.code())
        }

        run {
            assertEquals("Verify workout Name: ",workoutName , searchWorkouts.body()?.get(1)?.workoutName)
        }

        run {
            assertEquals("Verify workout Description: ",workoutDescription, searchWorkouts.body()?.get(1)?.description)
        }

        // delete newly added workout
        run {
            val deleteWorkout = api.deleteWorkoutByUser(tokenFactory(), workoutName).execute()
            assertEquals(201, deleteWorkout.code())
        }
    }

}