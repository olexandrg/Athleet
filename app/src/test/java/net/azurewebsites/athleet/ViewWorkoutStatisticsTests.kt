package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*

class ViewWorkoutStatisticsTests {
    private fun apiFactory(): Api {return Api.createSafe("https://testapi.athleetapi.club/api/")}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

//    @Test
//    fun testUserInsertAndDelete() {
//        //this generates a random string to append to the end of the username so that it is unique when the test is ran
//        //this will prevent issues if multiple people run the test at the same time or the test fails for someone and the user isn't deleted at the end
//        val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
//        val randomString = (1..6).map { i -> kotlin.random.Random.nextInt(0, charPool.size)}
//            .map(charPool::get).joinToString("")
//        //declare var's and val's used throughout the whole method
//        val userName = "TestUser" + randomString
//        var userId: Int? = 0
//        val api = apiFactory()
//
//        //add user
//        run {
//            val user = UserItem(
//                firebaseUID = "dummyid2",
//                userName = userName,
//                userHeadline = "Test Add headline",
//                userId = null
//            )
//            val cResponse = api.addNewUser(tokenFactory(), user).execute()
//            val responseCode = cResponse.code()
//            userId = cResponse.body()?.userId
//            assertEquals("User creation", 201, responseCode)
//        }
//
//
//        //check userName
//        run {
//            val list = api.getAllUsers(tokenFactory()).execute().body()
//            val unResponse = userHandler.returnUserName(list, userName)
//            assertEquals("Username check", userName, unResponse)
//        }
//
//        //check userId
//        run {
//            val list = api.getAllUsers(tokenFactory()).execute().body()
//            val response = userHandler.returnUserID(list, userName)
//            assertEquals("User id check", userId, response)
//        }
//
//        //delete user
//        val dList = api.getAllUsers(tokenFactory()).execute().body()
//        val userID = userHandler.returnUserID(dList, userName)
//        val dResponse = api.deleteUserByName(tokenFactory(), userID).execute().code()
//        assertEquals("User deletion", 200, dResponse)
//    }

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