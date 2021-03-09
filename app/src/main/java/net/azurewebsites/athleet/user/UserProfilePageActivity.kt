package net.azurewebsites.athleet.user

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.Charset

class UserProfilePageActivity : AppCompatActivity() {
    // store user data locally
    val api = Api.createSafe()
    private var userName = ""
    private var userHeadline = ""
    private var userEmail = FirebaseAuth.getInstance().currentUser?.email

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        // populate menu with retrieved user data
        getUserMenuData()
        findViewById<TextView>(R.id.userEmail).text = userEmail
    }

    // retrieve user data
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserMenuData() {
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener { response ->
            if(response.isSuccessful) {
                var token = "Bearer " + response.result?.token.toString()
                val callGetUser = api.checkExistingUser(token)

                callGetUser.enqueue(object: Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(response.isSuccessful) {
                            var response1 = response.body()?.source()?.readString(charset = Charset.defaultCharset())
                            val userData = response1?.split("[", "]", "{", "}", ",",":")?.map { it.trim()}
                            userName = userData?.get(5).toString()
                            userHeadline = userData?.get(9).toString()

                            findViewById<TextView>(R.id.userName).text = userName
                            findViewById<TextView>(R.id.userHeadline).text = userHeadline
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }
}