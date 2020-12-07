package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*

class ViewWorkoutStatisticsTests {
    fun apiFactory(): Api {return Api.createSafe("http://athleetapi-dev.us-west-2.elasticbeanstalk.com/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDczODE1MzcsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNzM4MTUzNywiZXhwIjoxNjA3Mzg1MTM3LCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.FaCOdF-NOdcegBU27y1pOUuglFHNCIv0Us5KTEVgPtTWXfquvVktjVnoexenm2pcvUVFkLZrv8vFMtXtApCAPAKUWK-aONh6iGdpdvZ_Ow1BIXYPvJPOgIe_JxN_KDwUn_Vy-cIxmzUTmnIQgW0P5ub24_QBpKK4UjiDwpuQlUjjhWP23_-PDYFpKtA_8j_kqEEPAqw6mGiYwLOwzo1HFoPxEpX5oOwuhvSoVuQaTXBaMIwTtk-VjwQKLjKccR0kjkG6R5vt0rZcsRHpu60sPMwxYApl5PkoOYVk41STQBY7v0CHzj5nTb8LOU9G3NWFoqL7R2ytLvy2EU8oZq9OMA"
        return "Bearer $firebaseToken"
    }
    @Test
    fun testUserInsertAndDelete() {
        //this generates a random string to append to the end of the username so that it is unique when the test is ran
        //this will prevent issues if multiple people run the test at the same time or the test fails for someone and the user isn't deleted at the end
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
        val randomString = (1..6).map { i -> kotlin.random.Random.nextInt(0, charPool.size)}
            .map(charPool::get).joinToString("")
        //declare var's and val's used throughout the whole method
        val userName = "TestUser" + randomString
        var userId: Int? = 0
        val api = apiFactory()

        //add user
        run {
            val user = UserItem(
                firebaseUID = "dummyid2",
                userName = userName,
                userHeadline = "Test Add headline",
                userId = null
            )
            val cResponse = api.addNewUser(tokenFactory(), user).execute()
            val responseCode = cResponse.code()
            userId = cResponse.body()?.userId
            assertEquals("User creation", 201, responseCode)
        }


        //check userName
        run {
            val list = api.getAllUsers(tokenFactory()).execute().body()
            val unResponse = userHandler.returnUserName(list, userName)
            assertEquals("Username check", userName, unResponse)
        }

        //check userId
        run {
            val list = api.getAllUsers(tokenFactory()).execute().body()
            val response = userHandler.returnUserID(list, userName)
            assertEquals("User id check", userId, response)
        }

        //delete user
        val dList = api.getAllUsers(tokenFactory()).execute().body()
        val userID = userHandler.returnUserID(dList, userName)
        val dResponse = api.deleteUserByName(tokenFactory(), userID).execute().code()
        assertEquals("User deletion", 200, dResponse)
    }
    @Test
    fun viewAllUserWorkouts() {
        // create new user
        val userName = "TestUser"
        var userId: Int? = 0

        // create new workout object
        val workout = WorkoutItem(
            workoutId = null,
            workoutName = "Test Workout",
            description = "Test workout description"
        )

        val api = apiFactory()

        //add user
        run {
            val user = UserItem(
                firebaseUID = "dummyid2",
                userName = userName,
                userHeadline = "Test Add headline",
                userId = null
            )
            val cResponse = api.addNewUser(tokenFactory(), user).execute()
            val responseCode = cResponse.code()
            userId = cResponse.body()?.userId
            assertEquals("User creation", 201, responseCode)
        }


        //check userName
        run {
            val list = api.getAllUsers(tokenFactory()).execute().body()
            val unResponse = userHandler.returnUserName(list, userName)
            assertEquals("Username check", userName, unResponse)
        }

        //check userId
        run {
            val list = api.getAllUsers(tokenFactory()).execute().body()
            val response = userHandler.returnUserID(list, userName)
            assertEquals("User id check", userId, response)
        }

        // add new workout workout to SQL Workouts table
        run {
            val response = api.addNewWorkout(tokenFactory(), workout).execute()
            val responseCode = response.code()
            assertEquals("Add new workout confirmation",201, responseCode)
        }


        // add existing workout for an existing user
        run {
            val userWorkout = UserWorkoutsItem(
                userWorkoutId = null,
                userId = userHandler.returnUserID(api.getAllUsers(tokenFactory()).execute().body(),userName),
                workoutId = workoutHandler.returnWorkoutID(api.getAllWorkouts(tokenFactory()).execute().body(), workout.workoutName),
                workoutDate = null
            )
            val response = api.addNewUserWorkout(tokenFactory(), userWorkout).execute().code()
            assertEquals("Add user workout confirmation: ",201, response)
        }

        // delete user workout
        run {
            val workoutsList = api.getAllWorkouts(tokenFactory()).execute().body()
            val userWorkoutsList = api.getAllUserWorkouts(tokenFactory()).execute().body()
            val userWorkoutId = UserWorkoutsItemsHandler.returnUserWorkoutID(
                workoutsList,
                userWorkoutsList,
                workout.workoutName)
            val response = api.deleteUserWorkout(tokenFactory(), userWorkoutId).execute().code()
            assertEquals("Delete user workout confirmation:",200, response)
        }

        // delete workout
        run {
            val response = api.deleteWorkoutByName(
                tokenFactory(),
                workoutHandler.
                returnWorkoutID(
                    api.getAllWorkouts(tokenFactory()).execute().body(),
                    workout.workoutName)).execute().code()
            assertEquals("Delete workout confirmation: ",200, response)
        }

        //delete user
        val dList = api.getAllUsers(tokenFactory()).execute().body()
        val userID = userHandler.returnUserID(dList, userName)
        val dResponse = api.deleteUserByName(tokenFactory(), userID).execute().code()
        assertEquals("User deletion", 200, dResponse)
    }
}