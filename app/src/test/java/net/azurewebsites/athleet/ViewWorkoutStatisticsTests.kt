package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*

class ViewWorkoutStatisticsTests {

    @Test
    fun searchUserWorkout() {
        val api = apiFactory()

        val response = api.getWorkout(tokenFactory()).execute()
        val responseCode = response.code()

        assertEquals(200, responseCode)
        println(response)
    }
}