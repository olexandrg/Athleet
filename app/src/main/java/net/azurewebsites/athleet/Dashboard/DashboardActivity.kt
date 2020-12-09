package net.azurewebsites.athleet.Dashboard

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_dashboard.*
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Workouts.Workout
import net.azurewebsites.athleet.Workouts.WorkoutDetailActivity
import net.azurewebsites.athleet.Workouts.WorkoutListAdapter


class DashboardActivity : AppCompatActivity() {
    private val newWorkoutActivityRequestCode = 1
    private val workoutListViewModel by viewModels<WorkoutsListViewModel> { WorkoutsListViewModelFactory(this) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val workoutAdapter = WorkoutListAdapter { workout -> adapterOnClick(workout) }
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = workoutAdapter

        workoutListViewModel.workoutsLiveData.observe(this , {
            it?.let {
                workoutAdapter.submitList(it as MutableList<Workout>)

            }
        })

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()

        }
    }
    private fun adapterOnClick(Workout: Workout) {
        val intent = Intent(this, WorkoutDetailActivity()::class.java)
        intent.putExtra(WORKOUT_NAME, Workout.name)
        startActivity(intent)
    }

    /* Adds Workout to WorkoutList when FAB is clicked. */
    private fun fabOnClick() {
        val intent = Intent(this, AddWorkoutActivity::class.java)
        startActivityForResult(intent, newWorkoutActivityRequestCode)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts Workout into viewModel. */
        if (requestCode == newWorkoutActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val WorkoutName = data.getStringExtra(WORKOUT_NAME)
                val WorkoutDescription = data.getStringExtra(WORKOUT_DESCRIPTION)

                workoutListViewModel.insertWorkout(WorkoutName, WorkoutDescription)
            }
        }
    }
}

