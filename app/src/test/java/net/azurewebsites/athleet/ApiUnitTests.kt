package net.azurewebsites.athleet
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import net.azurewebsites.athleet.ApiLib.*
import net.azurewebsites.athleet.models.Exercise
import org.junit.Test
import org.junit.Assert.*
import java.nio.charset.Charset

class ApiUnitTests {

    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    private fun apiFactory(): Api {return Api.createSafe()}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    @Test
    fun getExercisesForWorkoutById_shouldMatchFirstExercise() {
        // Given
        val api = apiFactory()
        val expected = Exercise(2, "Test Exercise", "Test Exercise Description", "5")

        testScope.launch {
            // When
            val response = api.getExercisesForWorkout(tokenFactory(), "6")
            // Then
            assertEquals("GET /api/Exercises/workout/{workoutID}", expected, response[0])
        }
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
        println("\nBEGIN EDIT USER TEST\n")
        println("\nGetting the test user's current account information for restoring after test completes...\n")

        // get user to start off
        // store their user data for cross-checking at the end of the test
        val originalUser = api.retrieveExistingUser(tokenFactory()).execute().body()!![0];  println("\nOriginal Username: ${originalUser.userName}\nOriginal Headline: ${originalUser.userHeadline}\n")


        // change user info (where it's allowed) and update, making sure the request succeeds
        val originalTestUser = originalUser.copy(); println("\nBeginning first edit to user data...\n")
        originalTestUser.userHeadline = "First Test Headline";
        originalTestUser.userName = "First Test Username";  println("\nSending PUT request with API call updateExistingUser...\n")

        // make the API call and make sure it succeeds
        assertEquals(200, api.updateExistingUser(tokenFactory(), originalTestUser.userId.toString(),originalTestUser).execute().code());    println("\nFirst PUT request succeeded.\n");println("\nBeginning GET request with API call to retrieveExistingUser...\n")

        // get the user again
        val originalTestUserReturn = api.retrieveExistingUser(tokenFactory()).execute().body()!![0];    println("\nFirst Edited Username: ${originalTestUserReturn.userName}\nFirst Edited Headline: ${originalTestUserReturn.userHeadline}\n")

        // make sure the username and headline are the original test strings
        assertEquals(originalTestUserReturn.userHeadline, "First Test Headline")
        assertEquals(originalTestUserReturn.userName, "First Test Username")

        // make sure none of the fields that SHOULD NOT BE CHANGEABLE, have not changed
        assertEquals(originalTestUserReturn.firebaseUID, originalUser.firebaseUID)
        assertEquals(originalTestUserReturn.userId, originalUser.userId)

        // make sure they're truly different from the original user
        assertNotEquals(originalUser.userHeadline, originalTestUserReturn.userHeadline)
        assertNotEquals(originalUser.userName, originalTestUserReturn.userName)

        // for good measure
        // change user info (where it's allowed) and update, making sure the request succeeds
        val editedTestUser = originalTestUserReturn.copy();     println("\nBeginning second edit to user data...\n")
        editedTestUser.userHeadline = "Second Test Headline"
        editedTestUser.userName = "Second Test Username"
        assertEquals(200, api.updateExistingUser(tokenFactory(), editedTestUser.userId.toString(),editedTestUser).execute().code());println("\nSecond PUT request succeeded.\n");println("\nBeginning GET request with API call to retrieveExistingUser...\n")

        // get the user again
        val editedTestUserReturn = api.retrieveExistingUser(tokenFactory()).execute().body()!![0];
        println("\nSecond Edited Username: ${editedTestUserReturn.userName}\nSecond Edited Headline: ${editedTestUserReturn.userHeadline}\n")
        // make sure the username and headline are the original test strings
        assertEquals(editedTestUserReturn.userHeadline, "Second Test Headline")
        assertEquals(editedTestUserReturn.userName, "Second Test Username")

        // make sure they're truly different from the original user
        assertNotEquals(editedTestUserReturn.userHeadline, originalTestUserReturn.userHeadline)
        assertNotEquals(editedTestUserReturn.userName, originalTestUserReturn.userName)
        assertNotEquals(editedTestUserReturn.userHeadline, originalUser.userHeadline)
        assertNotEquals(editedTestUserReturn.userName, originalUser.userName)

        // make sure none of the fields that SHOULD NOT BE CHANGEABLE, have not changed
        assertEquals(editedTestUser.firebaseUID, originalUser.firebaseUID)
        assertEquals(editedTestUser.userId, originalUser.userId)

        println("\nOriginal Username: ${originalUser.userName}\n\t and \n Original Headline: ${originalUser.userHeadline} \n\t will now be returned to the database.\n")
        // put back the original user info, from before the test was ran, making sure the update succeeds.
        assertEquals(200, api.updateExistingUser(tokenFactory(), originalUser.userId.toString(),originalUser).execute().code())
        println("\nEdit test completed successfully.\n")
    }
    // GET Api response tests
    // returns 200 for successful GET from the Api
    // returns 401 if not successful; will need to replace token or check if the api service is up
}