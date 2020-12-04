package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    //var client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun apiFactory(): Api {return Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDcxMTQ3NjMsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNzExNDc2MywiZXhwIjoxNjA3MTE4MzYzLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.U5xLzhTONCwPkhAaEr1o1qAjjLDmtFMB3a0M4Oq6EVepARXHE01mR36qU_3Ns_iF3hi4NiUGRfm3zzk49rgoYVE1c9SxXXnvos7XGJzqbl8gPNUAVay65aMIapT7o0W7jV8qEWnVpfc-tP3lHLP0MtQFGSXhM3gTyt-KzalT0AHscJ9uprc4Fk6yV7mW4iLDIHptjEYOKX_jwXXA54CBnrwS0FSps8T67FJLVqPPd36dvXVKTDshn65cKZWKO8lJNZD0ZF7VHu5LaSG7CWWw3Y6W7v-FicB5EPPlM12UUX89N8chC31drbg2BNOhWAu8aLHDwhrp372eYPbtK0ao1A"
        return "Bearer $firebaseToken"
    }


    @Test
    // returns 200 for successful GET from the Api
    // returns 401 if not successful; will need to replace token or check if the api service is up
    fun displaySuccessfulConnection(){
        val api = apiFactory()
        val response = api.getAllUsers(tokenFactory()).execute()
        assertEquals(response.code(), 200)
    }

    @Test
    fun testUser() {
        //declare var's and val's used throughout the whole method
        val userName = "TestUser"
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
    // returns error message that user does not exist
    fun displayUserException() {
        val api = apiFactory()
        val list = api.getAllUsers(tokenFactory()).execute().body()
        val user = "safasdf3254" //garbage value on purpose
        val response = userHandler.returnUserName(list, user)
        assertEquals("User $user not found.", response)
    }

    @Test
    // get all workouts
    fun viewAllWorkouts() {
        val api = apiFactory()
        val response = api.getAllWorkouts(tokenFactory()).execute()
        val responseCode = response.code()
        assertEquals(200, responseCode )
    }

    //@Test
    // adds new workout
    fun addNewWorkout() {
        val api = apiFactory()
        val workout = WorkoutItem(
                workoutId = null,
                workoutName = "Test Workout",
                description = "Chest day workout"
        )
        val response = api.addNewWorkout(tokenFactory(), workout).execute()
        val responseCode = response.code()
        assertEquals(201, responseCode)
    }

    //@Test
    // deletes workout by name
    // does not work if workout is referenced to specific UserWorkouts
    fun deleteWorkoutByName() {
        val api = apiFactory()
        val list = api.getAllWorkouts(tokenFactory()).execute().body()
        val workout = "Test Workout"
        val workoutID = workoutHandler.returnWorkoutID(list, workout)
        val response = api.deleteWorkoutByName(tokenFactory(), workoutID).execute()
        val responseCode = response.code()
        assertEquals(200, responseCode)
    }

    //@Test
    // get user workouts list
    fun getUserWorkoutsList() {
        val api = apiFactory()
        val list = api.getAllUserWorkouts(tokenFactory()).execute()
        println(list)
    }

    @Test
    // full User Workout CRUD
    fun userWorkoutCRUD() {
        val api = apiFactory()

        // create new workout object
        val workout = WorkoutItem(
                workoutId = null,
                workoutName = "Test Workout",
                description = "Test workout description"
        )

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
                    userId = userHandler.returnUserID(api.getAllUsers(tokenFactory()).execute().body(),"SimiF"),
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

   }
}