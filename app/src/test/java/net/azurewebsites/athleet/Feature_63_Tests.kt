package net.azurewebsites.athleet

import android.util.Log
import kotlinx.coroutines.runBlocking
import net.azurewebsites.athleet.models.Exercise
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class Feature_63_Tests{

    private val api = apiFactory()
    private lateinit var exercise: Exercise

    @Before fun initialize() {
        exercise = Exercise(48, "Calf Test", "testing this unit test", "lbs", 10, 3, 420)
    }

    // TESTS FOR -> ApiProvider.updateExercise(token, exerciseId, Exercise)
    @Test
    fun `updateExercise should return code 204` () {

        // Given
        val expected = 204

        // When
        val response = api.updateExercise(tokenFactory(), 48, exercise).execute()

        // Then
        assertEquals("response code is 204", expected, response.code())
    }

    @Test
    fun `When path exerciseId does not match the body exerciseId then it should return bad request(400)`() {

        // Given
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
        val eId = 1234
        val exercise = Exercise(eId, "Calf Test", "testing this unit test", "lbs", 10, 3, 420)
        val expected = 404

        // When
        val response = api.updateExercise(tokenFactory(), eId, exercise).execute()

        // Then
        assertEquals("exerciseId not found", expected, response.code())
    }

    @Test
    fun `When updateExercise then getExercise should match what we PUT`() {

        // Given
        val eId = 2
        val api = apiFactory()
        val startExercise = runBlocking { api.getExercise(tokenFactory(), eId) }
        val expected = Exercise(eId, "Calf Test", "testing this unit test", "lbs", 10, 3, 420)

        // When
        api.updateExercise(tokenFactory(), eId, expected).execute()
        val result = runBlocking { api.getExercise(tokenFactory(), eId)}

        api.updateExercise(tokenFactory(), eId, startExercise).execute()
        val resetExercise = runBlocking { api.getExercise(tokenFactory(), eId) }

        // Then
        assertEquals(expected, result)
        assertEquals(startExercise, resetExercise)
    }

}