package net.azurewebsites.athleet

import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*

class WriteWorkoutStatisticsTests {
    fun apiFactory(): Api {return Api.createSafe("https://testapi.athleetapi.club/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjVmOTcxMmEwODczMTcyMGQ2NmZkNGEyYTU5MmU0ZGZjMmI1ZGU1OTUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MTA1MTM0ODIsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYxMDUxMzQ4MiwiZXhwIjoxNjEwNTE3MDgyLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.Njdzwlp-t3ZPhZEIRR95EhDwlHG7vxYNuj1AqhQxI6zKO8v8HmH3QeJzGzQ82sAFBJ4tcv5mBA3Lj2pBHVqYyKEUlC3mjuQM3eOr2H1NJIZ2bKARVaaxEnleEAkY-M9puFN0z72myC1hMVSqMy7syhBnYtyaNLn7MMPQxnBxX59ZefK7eX_5NHPoeP6bh7CV_dvyO0dQroH_lVzFPOLHAaw24w6iu0Gf5KiP1ZpehboHZbEBc0QLQFIsBMoMsHiPaO1EuOgu-5kGK_tipKDXWZOPAI-XDdCjVl1W9GKbFkSDPPDHnK-AosgMXtkCFQAuV9SEFu87ADoEZHSR5zeDVg"
        return "Bearer $firebaseToken"
    }

    @Test // returns 201 on successful insert
    fun insertThenDeleteNewWorkout() {
        val api = apiFactory()
        val workoutName = "Test Name Insert"
        val workoutDescription = "Test Desc Insert"

        val response = api.insertNewWorkoutByUser(tokenFactory(), workoutName, workoutDescription).execute()
        val searchWorkouts = api.viewUserWorkoutsByUser(tokenFactory()).execute()

        // verify insert worked
        run {
            assertEquals(201, response.code())
        }

        run {
            assertEquals("GET /ViewUserWorkouts", 200, searchWorkouts.code())
        }

        run {
            assertEquals("Verify workout Name: ",workoutName , searchWorkouts.body()?.get(1)?.workoutName)
        }

        run {
            assertEquals("Verify workout Description: ",workoutDescription, searchWorkouts.body()?.get(1)?.description)
        }

        // delete newly added workout
        run {
            val deleteWorkout = api.deleteWorkoutByUser(tokenFactory(), workoutName).execute()
            assertEquals(201, deleteWorkout.code())
        }
    }

}