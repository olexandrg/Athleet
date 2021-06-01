package net.azurewebsites.athleet.fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_dashboard.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.AddWorkoutActivity
import net.azurewebsites.athleet.Dashboard.WORKOUT_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Teams.TeamWorkoutListViewModel
import net.azurewebsites.athleet.Teams.TeamWorkoutListViewModelFactory
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.TeamWorkout
import net.azurewebsites.athleet.models.Workout
import net.azurewebsites.athleet.workouts.WorkoutDetailActivity
import net.azurewebsites.athleet.workouts.WorkoutListAdapter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamWorkoutFragment() : Fragment() {
    val api = Api.createSafe()
    private lateinit var teamName: String

    private val workoutListViewModel by viewModels<TeamWorkoutListViewModel> { TeamWorkoutListViewModelFactory(requireContext()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fab:View
    private lateinit var workoutListAdapter: WorkoutListAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        workoutListAdapter = WorkoutListAdapter { workout -> adapterOnClick(workout) }
        var args = TeamWorkoutFragmentArgs.fromBundle(requireArguments())
        Log.i("TEST", args.teamName)
        teamName = args.teamName
        getWorkouts(teamName)

    }

    //************************************************//
    // this call needs to change to get team workouts //
    //************************************************//
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getWorkouts(teamName: String) {
        workoutListViewModel.insertWorkouts(teamName)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        linearLayoutManager = LinearLayoutManager(context)
        val workoutAdapter = WorkoutListAdapter { workout -> adapterOnClick(workout) }
        workoutListViewModel.workoutsLiveData.observe(this.viewLifecycleOwner , { it.let { if(workoutListViewModel.workoutsLiveData.value!!.size != 0) workoutAdapter.submitList(it as MutableList<Workout>) } })
        val rootView = inflater.inflate(R.layout.fragment_team_workout, container, false)
        fab = rootView.findViewById(R.id.btn_addTeamWorkout)
        fab.setOnClickListener { fabOnClick() }
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView_TeamWorkout) as RecyclerView
        recyclerView.adapter = workoutAdapter
        recyclerView.layoutManager=linearLayoutManager

        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        getWorkouts(teamName)
        fab.setOnClickListener { fabOnClick() }
    }

    private fun adapterOnClick(Workout: Workout) {
        val intent = Intent(requireContext(), WorkoutDetailActivity()::class.java)
        intent.putExtra(WORKOUT_NAME, Workout.workoutName)
        intent.putExtra("WORKOUT_ID", Workout.workoutId)
        startActivityForResult(intent, 69)
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
                val workoutName = data.getStringExtra(WORKOUT_NAME).toString()
                val workoutDescription = data.getStringExtra(WORKOUT_DESCRIPTION).toString()
                val teamWorkout = TeamWorkout(workoutName, workoutDescription, teamName)
                val call = api.createTeamWorkout(getFirebaseTokenId(), teamWorkout)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if(response.isSuccessful) {
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    }
                })
            }
        }
    }
}