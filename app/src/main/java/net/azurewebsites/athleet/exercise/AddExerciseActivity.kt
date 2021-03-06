package net.azurewebsites.athleet.exercise

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import net.azurewebsites.athleet.R

const val EXERCISE_NAME = "name"
const val EXERCISE_DESCRIPTION = "description"
const val EXERCISE_REPS = "reps"
const val EXERCISE_SETS = "sets"
const val EXERCISE_UNIT_TYPE = "unit type"
const val EXERCISE_UNIT_COUNT = "unit count"

class AddExerciseActivity : AppCompatActivity() {
    private lateinit var addExerciseName: TextInputEditText
    private lateinit var addExerciseDescription: TextInputEditText
    private lateinit var addExerciseReps: TextInputEditText
    private lateinit var addExerciseSets: TextInputEditText
    private lateinit var addExerciseUnitCount: TextInputEditText
    private lateinit var switch: Switch
    private lateinit var radioButtons:RadioGroup
    private lateinit var radioButtonSelected: RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercise)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addExercise()
        }
        addExerciseName = findViewById(R.id.add_exercise_name)
        addExerciseDescription = findViewById(R.id.add_exercise_description)
        addExerciseReps = findViewById(R.id.add_exercise_repetitions)
        addExerciseSets = findViewById(R.id.add_exercise_sets)
        addExerciseUnitCount = findViewById(R.id.add_exercise_unit_count)
        radioButtons = findViewById(R.id.radio_button_group)
        radioButtons.isVisible=false;
        switch = findViewById(R.id.switch_use_units)
        switch.setOnClickListener { showRadioButtons() }
        // Get radio group selected status and text using button click event
    }
    fun radio_button_click(view: View){
        radioButtons.clearCheck()
        radioButtons.check(view.id)
        radioButtonSelected = radioButtons.findViewById(radioButtons.checkedRadioButtonId)
    }
    private fun showRadioButtons(){
        if(switch.isChecked){
            radioButtons.isVisible=true
        }
        else
            radioButtons.isVisible=false
    }
    private fun addExercise() {
        val resultIntent = Intent()

        if (addExerciseName.text.isNullOrEmpty() || addExerciseDescription.text.isNullOrEmpty() || addExerciseReps.text.isNullOrEmpty() || addExerciseSets.text.isNullOrEmpty())
             setResult(Activity.RESULT_CANCELED, resultIntent)
        else
        {
            val name = addExerciseName.text.toString()
            val description = addExerciseDescription.text.toString()
            val reps = addExerciseReps.text.toString()
            val sets = addExerciseSets.text.toString()
            var unitType:String = radioButtonSelected.text.toString()
            val unitCount = addExerciseUnitCount.text.toString()

            resultIntent.putExtra(EXERCISE_NAME, name)
            resultIntent.putExtra(EXERCISE_DESCRIPTION, description)
            resultIntent.putExtra(EXERCISE_REPS, reps)
            resultIntent.putExtra(EXERCISE_SETS, sets)
            resultIntent.putExtra(EXERCISE_UNIT_TYPE, unitType)
            resultIntent.putExtra(EXERCISE_UNIT_COUNT, unitCount)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}