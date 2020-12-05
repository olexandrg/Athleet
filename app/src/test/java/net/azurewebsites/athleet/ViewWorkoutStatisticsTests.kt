package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*

class ViewWorkoutStatisticsTests {
    fun apiFactory(): Api {return Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDcxMjk5OTIsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNzEyOTk5MiwiZXhwIjoxNjA3MTMzNTkyLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.D_xrsP1ZH8XXuD4V_ng-n8Jhb_PWtpB7YImr60nKDJJOk0S5c1YRUUutCTJSj9jCn-HYc-sWdyPIrZ9Cg4wjPJCA4R4WQJEy02iRum8V9kbJK594_cHLgDFLGe4A7wCYBvSD2OcabRxHlk5ynE6qnGrWwcJ5ys-tWk5CeARANJtR4s5SzCFQ6gEGIakKo8ikU2mGZ6oZruiPbZTD0O8IpBE6awaLGehTUn3rkZ48L5c2lL9LSCYAgaERUF-vKBZsZak6Yc_a4n02ZRKrm2lMWNENmnThzFrNjBRSu-vB0qnvJMFMgTz-SBmuIuPNetbKdL6NLlOvU5EuWggTdPSFGQ"
        return "Bearer $firebaseToken"
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

        // check workout data
//        run {
//            val response = workoutHandler.returnWorkout(api.getAllWorkouts(tokenFactory()).execute().body(), workout.workoutName)
//        }

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