package net.azurewebsites.athleet
import org.junit.Assert.assertEquals
import org.junit.Test

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