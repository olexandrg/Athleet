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

        val response = api.getWorkout(tokenFactory()).execute()
        val responseCode = response.code()
        val workoutsList = response.body()

        assertEquals(200, responseCode)
        println(response)
    }
}