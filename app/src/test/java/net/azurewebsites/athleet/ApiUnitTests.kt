package net.azurewebsites.athleet
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    //var client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun apiFactory(): Api {return Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")}
    fun tokenFactory(): String {
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjlhZDBjYjdjMGY1NTkwMmY5N2RjNTI0NWE4ZTc5NzFmMThkOWM3NjYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDY5NTAzMTcsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNjk1MDMxNywiZXhwIjoxNjA2OTUzOTE3LCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.MhFIzZNFsDEXF4DfC-MPDxqsELLRcMxfL5L_w8e5JouUhWzhRe4hfMEi87wTjAyxDdI6sBNQZHGE3kSCMqLFpxj77E48Pdz0IG2Cu-DK_5kRanQhKaNiZXglD3X60DSLnYLTZply4ds1wkL81Ne41wQJNG_wyV7N8EcHQJJ8tvPm-dSWqgrwpUOItc1IGytL3j6sGROPnvpQDB6JEeLILZdddIi3pnozG0p5jT7RNnj59uatq9WnRyv9QTfcIrDpsg3JM_wEhoM6nhUU-t0GdBqaREurY0lYmUsSJ4-rv4aahVmwhR0X04M2iJGYJpGGw-UDSUOr6RZ-y-q7Opc2Rw"
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
    // pulls user from the database by name
    fun displayUser(){
        val api = apiFactory()
        val list = api.getAllUsers(tokenFactory()).execute().body()
        val user = "TestUser"
        val response = userHander.userName(list, user)
        assertEquals(user, response)
    }

    @Test
    fun displayUserException() {
        val api = apiFactory()
        val list = api.getAllUsers(tokenFactory()).execute().body()
        val user = "safasdf3254" //garbage value on purpose
        val response = userHander.userName(list, user)
        assertEquals("User $user not found.", response)
    }
}