package net.azurewebsites.athleet.fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_workouts_list.*
import net.azurewebsites.athleet.Dashboard.*
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Workouts.Workout
import net.azurewebsites.athleet.Workouts.WorkoutDetailActivity
import net.azurewebsites.athleet.Workouts.WorkoutListAdapter

class WorkoutsListFragment() : Fragment() {

    //private val workoutListViewModel by viewModels<WorkoutsListViewModel> { WorkoutsListViewModelFactory(requireContext()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        linearLayoutManager = LinearLayoutManager(requireContext())
        val workoutAdapter = WorkoutListAdapter { workout -> adapterOnClick(workout) }
        val recyclerView = recyclerView_Workout
        recyclerView.adapter = workoutAdapter
        //workoutAdapter.submitList(requireActivity().)*/
        val fab: View = requireActivity().findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        /*linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView_Workouts.layoutManager = linearLayoutManager
        val workoutAdapter = WorkoutListAdapter { workout -> adapterOnClick(workout) }
        workoutListViewModel.workoutsLiveData.observe(this.viewLifecycleOwner , { it?.let { workoutAdapter.submitList(it as MutableList<Workout>) } })*/
        return inflater.inflate(R.layout.fragment_workouts_list, container, false)
    }
    private fun adapterOnClick(Workout: Workout) {
        val intent = Intent(requireContext(), WorkoutDetailActivity()::class.java)
        intent.putExtra(WORKOUT_NAME, Workout.name)
        startActivity(intent)
    }
    /* Adds Workout to WorkoutList when FAB is clicked. */
    private fun fabOnClick() {
        val intent = Intent(this.requireActivity(), AddWorkoutActivity::class.java)
        startActivityForResult(intent, 1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        //Inserts Workout into viewModel. */
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val WorkoutName = data.getStringExtra(WORKOUT_NAME)
                val WorkoutDescription = data.getStringExtra(WORKOUT_DESCRIPTION)
                //workoutListViewModel.insertWorkout(WorkoutName, WorkoutDescription)

            }
        }
    }
}