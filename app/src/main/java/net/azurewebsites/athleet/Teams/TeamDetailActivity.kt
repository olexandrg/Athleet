package net.azurewebsites.athleet.Teams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_team_dashboard.view.*
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.R

class TeamDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        //nav_host_fragment.teamName.text = intent.extras?.getString(TEAM_NAME)
        //this.findViewById<TextView>(R.id.teamName).text =
    }

}