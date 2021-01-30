package net.azurewebsites.athleet.fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_workouts_list.*
import net.azurewebsites.athleet.ApiLib.*
import net.azurewebsites.athleet.Dashboard.*
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Workouts.Workout
import net.azurewebsites.athleet.Workouts.WorkoutDetailActivity
import net.azurewebsites.athleet.Workouts.WorkoutList
import net.azurewebsites.athleet.Workouts.WorkoutListAdapter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutsListFragment() : Fragment() {
    val api = Api.createSafe("https://testapi.athleetapi.club/api/")
    private val workoutListViewModel by viewModels<WorkoutsListViewModel> { WorkoutsListViewModelFactory(requireContext()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fab:View
    private lateinit var workoutListAdapter: WorkoutListAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        workoutListAdapter = WorkoutListAdapter { workout -> adapterOnClick(workout) }
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener { response ->
            if(response.isSuccessful) {
                val token = "Bearer " + response.result?.token.toString()
                val userName = FirebaseAuth.getInstance().currentUser!!.displayName

                //### The comment block below will become the API call that gets all of the user's workouts when they navigate to their main dashboard.
                // It will crash for the time being.

               /* val call = api.getUserWorkouts("Bearer " + response.result?.token.toString())
                call.enqueue(object:Callback<List<UserWorkouts>>{
                    override fun onResponse(
                        call: Call<List<UserWorkouts>>,
                        response: Response<List<UserWorkouts>>
                    ) {
                        if(response.isSuccessful)
                        {
                            val results = response.body()!!.toList()
                            var workoutList:MutableList<WorkoutItem>? = null
                            for(UserWorkoutItem in results){
                                val workoutCall = api.getWorkout(token, UserWorkoutItem.workoutId.toString())
                                workoutCall.enqueue(object:Callback<WorkoutItem>{
                                    override fun onResponse(
                                        call: Call<WorkoutItem>,
                                        response: Response<WorkoutItem>
                                    ) {
                                        if(response.isSuccessful)
                                            workoutList!!.add(response.body()!!)
                                    }

                                    override fun onFailure(call: Call<WorkoutItem>, t: Throwable) {
                                        TODO("Not yet implemented")
                                    }
                                })
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<UserWorkouts>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })*/
            }

        }
        workoutListAdapter.submitList(WorkoutList(resources))
        fab = requireActivity().findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        linearLayoutManager = LinearLayoutManager(context)
        val workoutAdapter = WorkoutListAdapter { workout ->
           workoutListViewModel.dataSource.currentWorkout=workout; adapterOnClick(workout) }
        workoutListViewModel.workoutsLiveData.observe(this.viewLifecycleOwner , { it?.let { workoutAdapter.submitList(it as MutableList<Workout>) } })
        val rootView = inflater!!.inflate(R.layout.fragment_workouts_list, container, false)
        fab.setOnClickListener { fabOnClick() }
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView_Workout) as RecyclerView
        recyclerView.adapter = workoutAdapter
        recyclerView.layoutManager=linearLayoutManager
        return rootView
    }

    override fun onResume() {
        super.onResume()
        fab.setOnClickListener { fabOnClick() }
    }
    private fun adapterOnClick(Workout: Workout) {
        val intent = Intent(requireContext(), WorkoutDetailActivity()::class.java)
        intent.putExtra(WORKOUT_NAME, Workout.name)
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
                workoutListViewModel.insertWorkout(WorkoutName, WorkoutDescription)

            }
        }
        else if (requestCode == 69)
        {
            workoutListViewModel.dataSource.clearExerciseList()
        }
    }
}