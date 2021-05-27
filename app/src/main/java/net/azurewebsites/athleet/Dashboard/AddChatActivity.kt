package net.azurewebsites.athleet.Dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import net.azurewebsites.athleet.R

class AddChatActivity : AppCompatActivity() {

    private lateinit var teamName: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_chat)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addTeam()
        }

        teamName = findViewById(R.id.add_exercise_name)
    }

    private fun addTeam() {
        val resultIntent = Intent()

        if (teamName.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = teamName.text.toString()
            resultIntent.putExtra(TEAM_NAME, name)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}