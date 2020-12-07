package net.azurewebsites.athleet

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.azurewebsites.athleet.ApiLib.*
import net.azurewebsites.athleet.ApiLib.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button = findViewById<Button>(R.id.button)
        var apiText = findViewById<TextView>(R.id.textView)
        var userData = ""
        val apiProvider = Api.createSafe("http://athleetapi-dev.us-west-2.elasticbeanstalk.com/api/")
        //auth variables
        //below is a bogus token
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjlhZDBjYjdjMGY1NTkwMmY5N2RjNTI0NWE4ZTc5NzFmMThkOWM3NjYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDY3ODgwMDksInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNjc4ODAwOSwiZXhwIjoxNjA2NzkxNjA5LCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.lS9slg0CPqBqJRh5Tt_hH7lJ7g5Fv6ytR5qM-joTHyTIlO3YLNOPlwXlP7XqhB2LXlVYtMCV7RDllcxgQFdEXPUp5vhcwGoXlU3j-bRrkfh8IayNCWlXgOurDdS2uN6nnVa7A58i6A1FySoXg8azdDAc7g9Wn5crNIvCJvBdIgsTxO0-Zjn5czXwzPk6cAct9t86RLqdXxcKP49eGp3f0LXP63kyr-PsW0pYezSM9tvVRknuRygFDUuTR25_yFNscFXu9yRTJ8viUbkl-JX-E_68UCTRtku3e5LDlcHdiGv8qjLKl4jwI4meyrOR-01LCCmx8hsRS8_X2yZYX6MIYQ"
        //bearer used because of: https://stackoverflow.com/a/47157391
        val token = "Bearer $firebaseToken"

        button.setOnClickListener {
            //if out side of button it will crash because it was already executed
            val call = apiProvider.getAllUsers(token)
            call.enqueue(object : Callback<List<UserItem>> {
                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                    apiText.text = "failure"
                }

                override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>>) {
                    apiText.text = response.body()?.get(0)?.userName.toString()
                }
            })
        }
    }
}