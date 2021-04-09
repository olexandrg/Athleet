package net.azurewebsites.athleet.Teams

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.R

class TeamDetailActivity : AppCompatActivity() {
    val api = Api.createSafe()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
    }
}


