package net.azurewebsites.athleet
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.TestCoroutineScope
import net.azurewebsites.athleet.ApiLib.*
//import net.azurewebsites.athleet.models.AthleetApi
import org.junit.Test
import org.junit.Assert.*
import java.nio.charset.Charset


class ApiUnitTests {

    //private val dispatcher = TestCoroutineDispatcher()
    //private val testScope = TestCoroutineScope(dispatcher)

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

    @Test
    fun checkRetrieveUser() {
        val api = apiFactory()
        val response = api.retrieveExistingUser(tokenFactory()).execute().body()?.get(0)

        println(response!!.userId)
        println(response.userName)
        println(response.userHeadline)
        println(response.firebaseUID)
    }

    @Test
    fun checkEditUser() {
        val api = apiFactory()

        // get user to start off
        // store their user data for cross-checking at the end of the test
        val originalUser = api.retrieveExistingUser(tokenFactory()).execute().body()!![0]


        // change user info (where it's allowed) and update, making sure the request succeeds
        var originalTestUser = originalUser.copy()
        originalTestUser.userHeadline = "Original Test Headline"
        originalTestUser.userName = "Original Test Username"
        assertEquals(200, api.updateExistingUser(tokenFactory(), originalTestUser.userId.toString(),originalTestUser).execute().code())

        // get the user again
        originalTestUser = api.retrieveExistingUser(tokenFactory()).execute().body()!![0]

        // make sure the username and headline are the original test strings
        assertEquals(originalTestUser.userHeadline, "Original Test Headline")
        assertEquals(originalTestUser.userName, "Original Test Username")

        // make sure they're truly different from the original user
        assertNotEquals(originalUser.userHeadline, originalTestUser.userHeadline)
        assertNotEquals(originalUser.userName, originalTestUser.userName)

        // make sure none of the fields that SHOULD NOT BE CHANGEABLE, have not changed
        assertEquals(originalTestUser.firebaseUID, originalUser.firebaseUID)
        assertEquals(originalTestUser.userId, originalUser.userId)

        // for good measure
        // change user info (where it's allowed) and update, making sure the request succeeds
        var editedTestUser = originalUser.copy()
        editedTestUser.userHeadline = "Edited Test Headline"
        editedTestUser.userName = "Edited Test Username"
        assertEquals(200, api.updateExistingUser(tokenFactory(), editedTestUser.userId.toString(),editedTestUser).execute().code())

        // get the user again
        editedTestUser = api.retrieveExistingUser(tokenFactory()).execute().body()!![0]

        // make sure the username and headline are the original test strings
        assertEquals(editedTestUser.userHeadline, "Edited Test Headline")
        assertEquals(editedTestUser.userName, "Edited Test Username")

        // make sure they're truly different from the original user
        assertNotEquals(editedTestUser.userHeadline, originalTestUser.userHeadline)
        assertNotEquals(editedTestUser.userName, originalTestUser.userName)

        // make sure none of the fields that SHOULD NOT BE CHANGEABLE, have not changed
        assertEquals(editedTestUser.firebaseUID, originalUser.firebaseUID)
        assertEquals(editedTestUser.userId, originalUser.userId)

        // put back the original user info, from before the test was ran, making sure the update succeeds.
        assertEquals(200, api.updateExistingUser(tokenFactory(), originalUser.userId.toString(),originalUser).execute().code())
    }
    // GET Api response tests
    // returns 200 for successful GET from the Api
    // returns 401 if not successful; will need to replace token or check if the api service is up


    //@Test // Will break if test user's exercises gets cleared :(
//    fun getExercisesForWorkoutById_shouldMatchFirstExercise() {
//
//        // Given
//        val api = apiFactory()
//        val expected = Exercise(2, "Test Exercise", "Test Exercise Description", "5")
//
//        // When
//        testScope.launch {
//            val response = api.getExercisesForWorkout(tokenFactory(), "6")
//
//            // Then
//            assertEquals("GET /api/Exercises/workout/{workoutID}", expected, response[0])
//        }
//    }
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