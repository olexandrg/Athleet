package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    fun apiFactory(): Api {return Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDcxMjMzMTgsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNzEyMzMxOCwiZXhwIjoxNjA3MTI2OTE4LCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.3_AlFarFt9H__0GQNS4SAMvEKv5yt3zjAv6eUfYn3x1kXYE19AZGkgwUWQ95ibdISAqd7-f9Iv-hxGFwxMjfkk7calZal_3rU2guKpYam4CBClOEhOMJxlInb0MhvBsupkL8tQKGf1a2G-Yf5PFTTn9oNBOmNFHqsIuz44-p62EWJUKddr7y_d2e_90d5Wo5FffuHAF_ckUVXlR1PGfULji9vVWW5rdhTz4jYS5huECOxMbsJzEJtcAvnKSQD3aa668D1N6cVMip-ErKF3evDKqj60wS8SFv-FOtE4-zmUo5e2heXk-qGlsybGwgemkkqs5H7TvC1UHz3UdzD_4Y0Q"
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

    @Test
    // get all exercises
    fun getAllExercises() {
        val api = apiFactory()
        val response = api.getAllExercises(tokenFactory()).execute().code()
        assertEquals("View all exercises confirmation:",200, response)
    }

    @Test
    // tests add and delete of Exercise only
    fun exerciseAddAndDelete() {
        val api = apiFactory()
        val exercise = ExercisesItem(
            exerciseId = null,
            exerciseName = "Test Exercise",
            description = null,
            defaultReps = null
        )
        // add new new exercise
        run {
            val addResponse = api.addNewExercise(tokenFactory(), exercise).execute().code()
            assertEquals("Exercise add confirmation: ",201, addResponse)
        }

        // delete exercise
        run {
            val exerciseId = ExercisesHandler.returnExerciseID(
                api.getAllExercises(tokenFactory()).execute().body(),
                exercise.exerciseName)
            val deleteResponse = api.deleteExercise(tokenFactory(), exerciseId).execute().code()
            assertEquals("Delete exercise confirmation", 200, deleteResponse)
        }
    }

    @Test
    // fetches master list of Workout Exercises
    fun getAllWorkoutExercises() {
        val api = apiFactory()
        val response =  api.getAllWorkoutExercises(tokenFactory()).execute().code()
        assertEquals("Workout Exercises list confirmation: ", 200, response)
    }


}