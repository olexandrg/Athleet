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
import androidx.activity.viewModels
import net.azurewebsites.athleet.Dashboard.WorkoutsListViewModel
//import com.example.recyclersample.flowerDetail.FlowerDetailActivity

class MainActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreferences"
    private val workoutsListViewModel by viewModels<WorkoutsListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //var button = findViewById<Button>(R.id.button)
        var apiText = findViewById<TextView>(R.id.textView)
        var userData = ""
        val apiProvider = Api.createSafe("https://testapi.athleetapi.club/api/")
        //auth variables
        //below is a bogus token
        val firebaseToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5ODI2ZDA5Mzc3N2NlMDA1ZTQzYTMyN2ZmMjAyNjUyMTQ1ZTk2MDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MDc0NjQ3NjMsInVzZXJfaWQiOiJQTmRiZXNRV3F3T3RXTkxiaVBSam9xOEdLdnoyIiwic3ViIjoiUE5kYmVzUVdxd090V05MYmlQUmpvcThHS3Z6MiIsImlhdCI6MTYwNzQ2NDc2MywiZXhwIjoxNjA3NDY4MzYzLCJlbWFpbCI6ImdldC50b2tlbkB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnZXQudG9rZW5AdGVzdC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.QJYU23ScT9P3FmfiYvAKqDe_prM9bFhuzGvCxAmOJR_6tWhFu8wLnvWTw1U9VG9tzWFPcydUVBLdz5rjeVAoP9-2HEIQwH-p7K1cYa04gwUC08yyy9Xi5jmLcGjTTkPTcaZlabxNjU7KsZ1rgCaDRavK1cMluc4zfA09AWHRZcNiRH3sj1ZrcxH9cbo4jfh8rsIJiLBZl9NXnAHomn63JeSJGzeyubdCzr0juWiXHZ0I9KaxvZ_NnKCMbA-kTVA6rNOiW-c8eEiW0bxMn18lW37yvPM5REJ29lnJyRfi083-7CBs9iy6NpFR_oCI3eMCMY8Vs5kVG0vw2oLgUGzxRA"
        //bearer used because of: https://stackoverflow.com/a/47157391
        val token = "Bearer $firebaseToken"

//        button.setOnClickListener {
//            //if out side of button it will crash because it was already executed
//            val call = apiProvider.getAllUsers(token)
//            call.enqueue(object : Callback<List<UserItem>> {
//                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
//                    apiText.text = "failure"
//                }
//
//                override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>>) {
//                    apiText.text = response.body()?.get(0)?.userName.toString()
//                }
//            })
//        }

    }


}