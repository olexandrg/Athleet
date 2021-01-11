package net.azurewebsites.athleet
import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    fun apiFactory(): Api {return Api.createSafe("https://testapi.athleetapi.club/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjVmOTcxMmEwODczMTcyMGQ2NmZkNGEyYTU5MmU0ZGZjMmI1ZGU1OTUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MTAzOTk3ODAsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYxMDM5OTc4MCwiZXhwIjoxNjEwNDAzMzgwLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.Q_DIDvPIoOhOck42NgENL2a7_tTFOp_xfDFM9fmHBIrr7qwsMVDGzcxxawlYIR8vGDp1pZhI7LbwXg1azLeAT3Et-OGKLSb8B4qOmhlRA4OD3DCPIZofB3VabViXPhucGXJb8gQbYEp1xKLt_iQHe_aDVOz-L03eB_RYSu5C4Utq1Msf4S9PszrSFnPxNR9HKNH1AxVXSK9DxQOei5SwR3phFa98YhjrTH9TiJgztz0Bzznf4b08W2c5SDXoHqSPpWyNCZULvVufof9aRTLZHtJseq65OTq1DF6GNqK08RBj9OYvLx11l9cAVpghAzOnrY5ZHk_VA5Ik9bE0d_dnjA"
        //val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImUwOGI0NzM0YjYxNmE0MWFhZmE5MmNlZTVjYzg3Yjc2MmRmNjRmYTIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MTAzOTQ4NjAsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYxMDM5NDg2MCwiZXhwIjoxNjEwMzk4NDYwLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.oSbzc7bPV_tH0yn4EX--rSEjYmfrUYrP3rWXuqPzZnFBF5K3tFIEuCQwLpfdh1rwklL4VuU9_fxm2Ifeg6pFDmXvy16G7oiIktW9z43NiLw2xuNDBWChHnnRnWoccsTc08rtdXhmyahhzI890Vn1-Qt3CltwBvi2xiLl-U60xkhHhZV4tGAYnjl8Mekg8PdGi7zaiz3I0PImCUW2WTUy3tLPGdwAqJ04T5xNjkB3hng_RYzqTyNoFnpoB_mUfNh_gvC1r-XPNlwwAgt004us5H1_EKjqaPySvQMRd3iGJawgDPGXSrpujYFELcYD2fFj9ZsbDtPbACCGpLQ1CtSWlw"
        //val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImUwOGI0NzM0YjYxNmE0MWFhZmE5MmNlZTVjYzg3Yjc2MmRmNjRmYTIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MTAzOTE3OTEsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYxMDM5MTc5MSwiZXhwIjoxNjEwMzk1MzkxLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.J8pjlkTeBNh0_zomgL6zXkoQJ1fJASfcHDUh8-bx4Zz71BCypQe98QF-IyM9prGWev5loP6uJpLoUQY7BExRnw42GDLY8mjUjYYiYr2veLqjgEoDNULZuvcgJ4GLnGh99o-v0W-DlkvixDio4TxoY15dQR0RmG3Z4T4DdPR_QmRiYEU4sFOcctf5C-BsSs_i8qQ05Q8wCDZMHEkfmpoDskJJscLzEckCPR-hXx_o3siF_6Jic68HsCUYP8exrGzfHhojTFva2sa4rbetL2YpfjURGXoQKsha9ltNsIjpeClcORgEflCkTpX680uSnRfQoopDpaNYkeGYuZ5mT17T5A"
        //val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDc0NzcyMjIsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNzQ3NzIyMiwiZXhwIjoxNjA3NDgwODIyLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.S8CrNihaWJgwe_WBiTJr6xp1D4y5s5YFF8_qtSYspYq74mAT-aJzOKK84x6C4_k3PgtTghJ6Rq2P7QOmOl_ZEyOVz6D_rY4lXFKbU8Epm-nYYl0k3LOYMNAmG4QVicQdSYgAPeHolHMKJ5d6F3_xHKEqBdskQF8kIDaqr6UeeLGnfFiZ30f33uBZlY_xzOhV7K1zcuI8EDRqV3KB2ksUL64OIqgdiY9TDz0GpwqQShGvN9i4Yr2_24K9u3TIrrwYtihkiAoI60QTTlOY_J-5woQubqcADrmi4O5cXInbzyA6taE-nhbZH3RQYyXHFDFxd7LVX4HyGUrV-6v_ygG2ag"
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

    @Test // tests API response
    fun viewUserWorkoutsByFirebaseUID() {
        val api = apiFactory()
        val response = api.viewUserWorkoutsByUser(tokenFactory()).execute()

        run {
            assertEquals("GET /ViewUserWorkouts", 200, response.code())
        }

        run {
            assertEquals("Verify workout Name: ","Test Workout" , response.body()?.get(0)?.workoutName)
        }

        run {
            assertEquals("Verify workout Description: ","Test Description" , response.body()?.get(0)?.description)
        }
    }

    @Test // tests API response
    // known good json output:
    // [{"userName":"TestUseraGKhtr","workoutName":"Test Workout","exerciseName":"Test Exercise","description":"Test Exercise Description","defaultReps":5}]
    fun viewExerciseInWorkoutsByFirebaseUID() {
        val api = apiFactory()
        val response = api.viewUserWorkoutsByUser(tokenFactory()).execute()
        val workoutName = response.body()?.get(0)?.workoutName

        val exerciseFetched = api.viewExerciseInWorkoutByUser(tokenFactory(), workoutName).execute()
        val firstExercise = exerciseFetched.body()?.get(0)

        run {
            assertEquals("Verify exercise was added to correct workout: ", workoutName, firstExercise?.workoutName)
        }
        run {
            assertEquals("Verify exercise name: ", "Test Exercise", firstExercise?.exerciseName)
        }

        run {
            assertEquals("Verify exercise description: ", "Test Exercise Description", firstExercise?.description)
        }

        run {
            assertEquals("Verify default reps: ", 5, firstExercise?.defaultReps)
        }

    }
}