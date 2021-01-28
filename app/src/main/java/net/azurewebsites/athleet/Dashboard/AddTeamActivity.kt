package net.azurewebsites.athleet.Dashboard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import net.azurewebsites.athleet.R

const val TEAM_NAME = "name"
const val TEAM_DESCRIPTION = "description"
class AddTeamActivity : AppCompatActivity() {

    private lateinit var teamName: TextInputEditText
    private lateinit var teamDescription: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_team)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addTeam()
        }

        teamName = findViewById(R.id.add_exercise_name)
        teamDescription = findViewById(R.id.add_team_description)
    }

    private fun addTeam() {
        val resultIntent = Intent()

        if (teamName.text.isNullOrEmpty() || teamDescription.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = teamName.text.toString()
            val description = teamDescription.text.toString()
            resultIntent.putExtra(TEAM_NAME, name)
            resultIntent.putExtra(TEAM_DESCRIPTION, description)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}