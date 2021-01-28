package net.azurewebsites.athleet.Workouts

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.azurewebsites.athleet.Dashboard.AddWorkoutActivity
import net.azurewebsites.athleet.Dashboard.WORKOUT_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
import net.azurewebsites.athleet.Dashboard.WorkoutsListViewModel
import net.azurewebsites.athleet.Dashboard.WorkoutsListViewModelFactory
import net.azurewebsites.athleet.Exercises.*
import net.azurewebsites.athleet.R

class WorkoutDetailActivity : AppCompatActivity() {
    private val exerciseListViewModel by viewModels<ExerciseListViewModel> { ExerciseListViewModelFactory(this) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var exerciseAdapter:ExerciseListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_detail)
        linearLayoutManager = LinearLayoutManager(this)
        findViewById<Button>(R.id.btn_complete_new).setOnClickListener { setResult(1) ; finish(); }
        findViewById<Button>(R.id.btn_cancel).setOnClickListener { setResult(0) ; finish(); }
        findViewById<FloatingActionButton>(R.id.fab_exercise).setOnClickListener { val intent = Intent(this, AddExerciseActivity::class.java)
            startActivityForResult(intent,4) }
        exerciseAdapter = ExerciseListAdapter { exercise -> adapterOnClick(exercise) }
        exerciseListViewModel.exercisesLiveData.observe(this , { it?.let { exerciseAdapter.submitList(it as MutableList<Exercise>) } })
        val recyclerView = findViewById<RecyclerView>(R.id.recView_ExerciseList) as RecyclerView
        recyclerView.adapter = exerciseAdapter
        recyclerView.layoutManager = linearLayoutManager
    }
    private fun adapterOnClick(Exercise: Exercise) {

    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
            data?.let { data ->
                val ExerciseName = data.getStringExtra(EXERCISE_NAME)
                val ExerciseDescription = data.getStringExtra(EXERCISE_DESCRIPTION)
                val ExerciseReps = data.getStringExtra(EXERCISE_REPS)!!.toInt()
                val ExerciseSets = data.getStringExtra(EXERCISE_SETS)!!.toInt()
                val ExerciseUnitType = data.getStringExtra(EXERCISE_UNIT_TYPE)
                val ExerciseUnitCount = data.getStringExtra(EXERCISE_UNIT_COUNT)!!.toFloat()
                exerciseListViewModel.insertExercise(ExerciseName, ExerciseDescription, ExerciseReps, ExerciseSets, ExerciseUnitType!!, ExerciseUnitCount)

            }
        }


    }
}