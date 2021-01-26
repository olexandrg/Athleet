package net.azurewebsites.athleet.Teams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.ActivityTeamDetailBinding

class TeamDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityTeamDetailBinding>(this, R.layout.activity_team_detail)

    }
}