package net.azurewebsites.athleet

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.ApiLib.Api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InviteTeamUser : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_team_user)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            inviteUser()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun inviteUser() {
        var userName = findViewById<TextInputEditText>(R.id.add_user_name).text.toString()
        inviteUserToTeam(userName)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun inviteUserToTeam(userName : String) {
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener() { response ->
            if (response.isSuccessful) {
                val api = Api.createSafe()
                val teamName = intent?.getStringExtra("name")!!
                val apiCall = api.inviteExistingUserToTeam(getFirebaseTokenId(), teamName, userName)
                apiCall.enqueue(object: Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        // unable to show toast, this is not recognized, probably due to thread issue
                        // Toast.makeText(this, "User is now added.", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        // unable to show toast, this is not recognized, probably due to thread issue
                        // Toast.makeText(this, "Failed finding user name or team name.", Toast.LENGTH_LONG).show()
                    }
                })
            }
            else { Toast.makeText(this, "Failed getting token of user", Toast.LENGTH_LONG).show() }
        }
    }
}