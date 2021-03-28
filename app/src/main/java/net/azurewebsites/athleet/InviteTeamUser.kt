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
import net.azurewebsites.athleet.models.TeamInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InviteTeamUser : AppCompatActivity() {
    private val _activity = this
    private val teamList = ArrayList<Pair<String, Boolean>>()
    private val currentUserUserName = FirebaseAuth.getInstance().currentUser!!.displayName!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_team_user)
        getTeamUsers()

        findViewById<Button>(R.id.done_button).setOnClickListener { inviteUser() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun inviteUser() {
        val desiredUserToBeInvited = findViewById<TextInputEditText>(R.id.add_user_name).text.toString()
        if (isAdmin(currentUserUserName)) {
            inviteUserToTeam(desiredUserToBeInvited)
        }

        // end intent and go back to user list
        finish()
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
                        Toast.makeText(_activity, "User is now added.", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(_activity, "Failed finding user name or team name.", Toast.LENGTH_LONG).show()
                    }
                })
            }
            else { Toast.makeText(_activity, "Failed getting token of user", Toast.LENGTH_LONG).show() }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTeamUsers()
    {
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener() { response ->
            if (response.isSuccessful) {
                val teamName = _activity?.intent?.getStringExtra("name")!!
                val api = Api.createSafe()
                val apiCall = api.teamInfo(getFirebaseTokenId(), teamName)
                apiCall.enqueue(object: Callback<TeamInfo>{
                    override fun onResponse(call: Call<TeamInfo>, response: Response<TeamInfo>) {
                        if (response.isSuccessful) {
                            val userList = response.body()!!.users
                            for (user in userList) {
                                teamList.add(Pair(user.userName, user.isAdmin))
                            }
                        }
                    }
                    override fun onFailure(call: Call<TeamInfo>, t: Throwable) {
                        Toast.makeText(_activity, "Failed getting the team users", Toast.LENGTH_LONG).show()
                    }
                })
            }
            else { Toast.makeText(_activity, "Failed getting token of user", Toast.LENGTH_LONG).show() }
        }
    }

    private fun isAdmin(userName: String) : Boolean {
        for (teamUser in teamList) {
            if (userName == teamUser.first && teamUser.second) { return true }
        }
        return false
    }
}