package net.azurewebsites.athleet
import org.junit.Test
import org.junit.Assert.*


class ApiUnitTests {
    //var client = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    @Test
    fun displayMessage(){
        val api = Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjlhZDBjYjdjMGY1NTkwMmY5N2RjNTI0NWE4ZTc5NzFmMThkOWM3NjYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDY3ODgwMDksInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNjc4ODAwOSwiZXhwIjoxNjA2NzkxNjA5LCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.lS9slg0CPqBqJRh5Tt_hH7lJ7g5Fv6ytR5qM-joTHyTIlO3YLNOPlwXlP7XqhB2LXlVYtMCV7RDllcxgQFdEXPUp5vhcwGoXlU3j-bRrkfh8IayNCWlXgOurDdS2uN6nnVa7A58i6A1FySoXg8azdDAc7g9Wn5crNIvCJvBdIgsTxO0-Zjn5czXwzPk6cAct9t86RLqdXxcKP49eGp3f0LXP63kyr-PsW0pYezSM9tvVRknuRygFDUuTR25_yFNscFXu9yRTJ8viUbkl-JX-E_68UCTRtku3e5LDlcHdiGv8qjLKl4jwI4meyrOR-01LCCmx8hsRS8_X2yZYX6MIYQ"
        //bearer used because of: https://stackoverflow.com/a/47157391
        val token = "Bearer $firebaseToken"
        val response = api.getAllUsers(token).execute()
        assertEquals(response.code(), 200)
    }

    @Test
    fun displayUser(){
        val api = Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjlhZDBjYjdjMGY1NTkwMmY5N2RjNTI0NWE4ZTc5NzFmMThkOWM3NjYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDY3ODgwMDksInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNjc4ODAwOSwiZXhwIjoxNjA2NzkxNjA5LCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.lS9slg0CPqBqJRh5Tt_hH7lJ7g5Fv6ytR5qM-joTHyTIlO3YLNOPlwXlP7XqhB2LXlVYtMCV7RDllcxgQFdEXPUp5vhcwGoXlU3j-bRrkfh8IayNCWlXgOurDdS2uN6nnVa7A58i6A1FySoXg8azdDAc7g9Wn5crNIvCJvBdIgsTxO0-Zjn5czXwzPk6cAct9t86RLqdXxcKP49eGp3f0LXP63kyr-PsW0pYezSM9tvVRknuRygFDUuTR25_yFNscFXu9yRTJ8viUbkl-JX-E_68UCTRtku3e5LDlcHdiGv8qjLKl4jwI4meyrOR-01LCCmx8hsRS8_X2yZYX6MIYQ"
        //bearer used because of: https://stackoverflow.com/a/47157391
        val token = "Bearer $firebaseToken"
        val user = api.getUserByName(token, "SimiF").execute().body()?.get(0)?.userName.toString()
        assertEquals("SimiF", user)
    }
}