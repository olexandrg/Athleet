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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_exercise.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.AddWorkoutActivity
import net.azurewebsites.athleet.Dashboard.WORKOUT_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.Workout
import net.azurewebsites.athleet.workouts.WorkoutDetailActivity
import net.azurewebsites.athleet.workouts.WorkoutListAdapter
import net.azurewebsites.athleet.workouts.WorkoutsListViewModel
import net.azurewebsites.athleet.workouts.WorkoutsListViewModelFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutsListFragment() : Fragment() {
    val api = Api.createSafe()
    private val workoutListViewModel by viewModels<WorkoutsListViewModel> { WorkoutsListViewModelFactory(requireContext()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fab:View
    private lateinit var workoutListAdapter: WorkoutListAdapter
    private lateinit var token:String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        workoutListAdapter = WorkoutListAdapter { workout -> adapterOnClick(workout) }
        getWorkouts()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getWorkouts() {
        token = getFirebaseTokenId()
        val callGetWorkouts = api.getWorkout(token)
        callGetWorkouts.enqueue(object:Callback<List<Workout>>{
            override fun onResponse(call: Call<List<Workout>>, response: Response<List<Workout>>) { if(response.isSuccessful) { workoutListViewModel.insertWorkouts(response.body()!!.toList()) } }
            override fun onFailure(call: Call<List<Workout>>, t: Throwable) { TODO("Not yet implemented") }
        })
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        linearLayoutManager = LinearLayoutManager(context)
        val workoutAdapter = WorkoutListAdapter { workout -> adapterOnClick(workout) }
        workoutListViewModel.workoutsLiveData.observe(this.viewLifecycleOwner , { it.let { if(workoutListViewModel.workoutsLiveData.value!!.size != 0) workoutAdapter.submitList(it as MutableList<Workout>) } })
        val rootView = inflater!!.inflate(R.layout.fragment_workouts_list, container, false)
        fab = requireActivity().fab
        fab.setOnClickListener { fabOnClick() }
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView_Workout) as RecyclerView
        recyclerView.adapter = workoutAdapter
        recyclerView.layoutManager=linearLayoutManager

        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        getWorkouts()
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
                val WorkoutName = data.getStringExtra(WORKOUT_NAME)
                val WorkoutDescription = data.getStringExtra(WORKOUT_DESCRIPTION)
                val call = api.addNewWorkout(token, WorkoutName!!, WorkoutDescription!!)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if(response.isSuccessful) {
                            Log.i("API Call Insert Workout", "Insert workout sucessful. New WorkoutName: $WorkoutName")
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }
}