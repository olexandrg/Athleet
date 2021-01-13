package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    fun apiFactory(): Api {return Api.createSafe("https://testapi.athleetapi.club/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjVmOTcxMmEwODczMTcyMGQ2NmZkNGEyYTU5MmU0ZGZjMmI1ZGU1OTUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MTA1MTM0ODIsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYxMDUxMzQ4MiwiZXhwIjoxNjEwNTE3MDgyLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.Njdzwlp-t3ZPhZEIRR95EhDwlHG7vxYNuj1AqhQxI6zKO8v8HmH3QeJzGzQ82sAFBJ4tcv5mBA3Lj2pBHVqYyKEUlC3mjuQM3eOr2H1NJIZ2bKARVaaxEnleEAkY-M9puFN0z72myC1hMVSqMy7syhBnYtyaNLn7MMPQxnBxX59ZefK7eX_5NHPoeP6bh7CV_dvyO0dQroH_lVzFPOLHAaw24w6iu0Gf5KiP1ZpehboHZbEBc0QLQFIsBMoMsHiPaO1EuOgu-5kGK_tipKDXWZOPAI-XDdCjVl1W9GKbFkSDPPDHnK-AosgMXtkCFQAuV9SEFu87ADoEZHSR5zeDVg"
        return "Bearer $firebaseToken"
    }

    // GET Api response tests
    // returns 200 for successful GET from the Api
    // returns 401 if not successful; will need to replace token or check if the api service is up

    @Test // tests API response
    fun displayAllUsers(){
        val api = apiFactory()
        val response = api.getAllUsers(tokenFactory()).execute()
        assertEquals("GET api/Users", response.code(), 200)
    }

    @Test // tests API response
    fun displayUserException() {
        val api = apiFactory()
        val list = api.getAllUsers(tokenFactory()).execute().body()
        val user = "safasdf3254" //garbage value on purpose
        val response = userHandler.returnUserName(list, user)
        assertEquals("User $user not found.", response)
    }

    @Test // tests API response
    fun displayAllWorkouts() {
        val api = apiFactory()
        val response = api.getAllWorkouts(tokenFactory()).execute()
        val responseCode = response.code()
        assertEquals("GET /api/Workouts ",200, responseCode )
    }

    @Test // tests API response
    fun getUserWorkoutsList() {
        val api = apiFactory()
        val responseCode = api.getAllUserWorkouts(tokenFactory()).execute().code()
        assertEquals("GET /api/UserWorkouts ",200, responseCode )
    }

    @Test // tests API response
    fun getAllExercises() {
        val api = apiFactory()
        val response = api.getAllExercises(tokenFactory()).execute().code()
        assertEquals("GET /api/Exercises",200, response)
    }

    @Test // tests API response
    fun getAllWorkoutExercises() {
        val api = apiFactory()
        val response =  api.getAllWorkoutExercises(tokenFactory()).execute().code()
        assertEquals("GET /api/WorkoutExercises ", 200, response)
    }
}