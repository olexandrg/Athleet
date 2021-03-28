package net.azurewebsites.athleet.Teams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.azurewebsites.athleet.ApiLib.TeamInfo
import net.azurewebsites.athleet.R

class TeamDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
    }
}