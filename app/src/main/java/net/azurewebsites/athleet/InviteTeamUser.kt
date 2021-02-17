package net.azurewebsites.athleet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class InviteTeamUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_team_user)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            inviteUser()
        }
    }

    // TODO: this will require API calls to look up usernames
    fun inviteUser() {
        finish()
    }
}