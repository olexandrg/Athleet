package net.azurewebsites.athleet

import android.util.Log
import kotlinx.coroutines.runBlocking
import net.azurewebsites.athleet.models.Exercise
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class Feature_63_Tests{

    private val api = apiFactory()

    // TESTS FOR -> ApiProvider.updateExercise(token, exerciseId, Exercise)
    @Test
    fun `updateExercise should return code 204` () {

        // Given
        val exercise = Exercise(48, "Calf Test", "testing this unit test", "lbs", 10, 3, 420)
        val expected = 204

        // When
        val response = api.updateExercise(tokenFactory(), 48, exercise).execute()

        // Then
        assertEquals("response code is 204", expected, response.code())
    }

    @Test
    fun `When path exerciseId does not match the body exerciseId then it should return bad request(400)`() {

        // Given
        val exercise = Exercise(48, "Calf Test", "testing this unit test", "lbs", 10, 3, 420)
        val eId = 42
        val expected = 400

        // When
        val response = api.updateExercise(tokenFactory(), eId, exercise).execute()

        // Then
        assertEquals("mismatch exerciseId return 400", expected, response.code())
    }

    @Test
    fun `When exerciseId does not exist then it should return not found(404)`() {

        // Given
        val exercise = Exercise(1234, "Calf Test", "testing this unit test", "lbs", 10, 3, 420)
        val eId = 1234
        val expected = 404

        // When
        val response = api.updateExercise(tokenFactory(), eId, exercise).execute()

        // Then
        assertEquals("exerciseId not found", expected, response.code())
    }

}