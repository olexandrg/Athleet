package net.azurewebsites.athleet

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        val apiProvider = Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")
        //auth variables
        //below is a bogus token
        val firebaseToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
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

