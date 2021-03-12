package net.azurewebsites.athleet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText

class InviteTeamUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_team_user)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            inviteUser()
        }
    }

    private fun inviteUser() {
        var userName = findViewById<TextInputEditText>(R.id.add_user_name)
        inviteUserToTeam(userName)
    }

    private fun inviteUserToTeam(userName : String) {

    }
}