package net.azurewebsites.athleet.Dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import net.azurewebsites.athleet.R

const val WORKOUT_NAME = "name"
const val WORKOUT_DESCRIPTION = "description"
class AddWorkoutActivity : AppCompatActivity() {
    private lateinit var addWorkoutName: TextInputEditText
    private lateinit var addWorkoutDescription: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_workout_layout)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addWorkout()
        }
        addWorkoutName = findViewById(R.id.add_workout_name)
        addWorkoutDescription = findViewById(R.id.add_workout_description)
    }
    private fun addWorkout() {
        val resultIntent = Intent()

        if (addWorkoutName.text.isNullOrEmpty() || addWorkoutDescription.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = addWorkoutName.text.toString()
            val description = addWorkoutDescription.text.toString()
            resultIntent.putExtra(WORKOUT_NAME, name)
            resultIntent.putExtra(WORKOUT_DESCRIPTION, description)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}