package net.azurewebsites.athleet.workouts

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.AddWorkoutActivity
import net.azurewebsites.athleet.Dashboard.WORKOUT_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.FragmentWorkoutBinding
import net.azurewebsites.athleet.exercise.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WorkoutFragment : Fragment() {
    val api = Api.createSafe()
    private lateinit var token:String
    private lateinit var workoutName: String
    var workoutId:Int = 0
    private val viewModel: WorkoutViewModel by lazy { ViewModelProvider(this).get(WorkoutViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var workoutId = requireActivity().intent.extras!!.getInt("WORKOUT_ID")
        this.workoutId = workoutId
        Log.i("WORKOUT ID", workoutId.toString())
        val binding = DataBindingUtil.inflate<FragmentWorkoutBinding>(inflater,
            R.layout.fragment_workout, container, false )

        binding.lifecycleOwner = this
        viewModel.getExercises(workoutId)
        binding.viewModel = viewModel
        binding.exercisesListRecView.adapter = ExerciseListAdapter(ExerciseListAdapter.OnClickListener {
            viewModel.displayExerciseDetails(it)
        })
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener { response -> if(response.isSuccessful) { token = "Bearer " + response.result?.token.toString()}}
        viewModel.navigateToSelectedExercise.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(
                    WorkoutFragmentDirections.actionWorkoutFragmentToExerciseFragment(it))
                viewModel.displayExerciseDetailsComplete()
            }
        })

        binding.workoutName.text = requireActivity().intent.extras?.getString(WORKOUT_NAME).toString()
        workoutName = requireActivity().intent.extras?.getString(WORKOUT_NAME).toString()
        binding.fabAddExercise.setOnClickListener { view: View ->
            Log.i("WorkoutFragment", "Fab add exercise clicked")
            val intent = Intent(this.requireActivity(), AddExerciseActivity::class.java)
            startActivityForResult(intent, 609)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 609 && resultCode == Activity.RESULT_OK) {
            data?.let { data ->
                val ExerciseName = data.getStringExtra(EXERCISE_NAME)
                val ExerciseDescription = data.getStringExtra(EXERCISE_DESCRIPTION)
                val Reps = data.getStringExtra(EXERCISE_REPS)!!.toInt()
                val Sets = data.getStringExtra(EXERCISE_SETS)!!.toInt()
                val UnitCount = data.getStringExtra(EXERCISE_UNIT_COUNT)!!.toInt()
                val UnitType = data.getStringExtra(EXERCISE_UNIT_TYPE)

                insertExercise(ExerciseName!!,ExerciseDescription!!,Reps,Sets,UnitType!!,UnitCount)

            }
        }
    }
    fun insertExercise(eName:String, eDesc:String, eReps:Int, eSets:Int, eUnitType:String, eUnitCount:Int)
    {
        val call = api.addNewExercise(token, eName, eDesc, eReps, eSets, eUnitType, eUnitCount, workoutId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    Log.i("API Insert Exercise", "Insert exercise sucessful. New ExerciseName: $eName")
                    viewModel.getExercises(workoutId)
                    Log.i("Workout Fragment:", "Updated exercise list requested")} }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) { TODO("Not yet implemented") }
        })
    }
}