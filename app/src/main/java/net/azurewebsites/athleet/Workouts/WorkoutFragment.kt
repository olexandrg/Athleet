package net.azurewebsites.athleet.Workouts

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
import net.azurewebsites.athleet.Exercises.*
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.FragmentTeamDashboardBinding
import net.azurewebsites.athleet.databinding.FragmentWorkoutBinding


class WorkoutFragment : Fragment() {

    //private val exerciseListViewModel by viewModels<ExerciseListViewModel> { ExerciseListViewModelFactory(this) }
    //private lateinit var linearLayoutManager: LinearLayoutManager
    //private lateinit var exerciseAdapter: ExerciseListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentWorkoutBinding>(inflater,
            R.layout.fragment_workout, container, false )

        binding.workoutName.text = requireActivity().intent.extras?.getString(WORKOUT_NAME).toString()

        return binding.root
    }


//    private fun adapterOnClick(Exercise: Exercise) {
//
//    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
//            data?.let { data ->
//                val ExerciseName = data.getStringExtra(EXERCISE_NAME)
//                val ExerciseDescription = data.getStringExtra(EXERCISE_DESCRIPTION)
//                val ExerciseReps = data.getStringExtra(EXERCISE_REPS)!!.toInt()
//                val ExerciseSets = data.getStringExtra(EXERCISE_SETS)!!.toInt()
//                val ExerciseUnitType = data.getStringExtra(EXERCISE_UNIT_TYPE)
//                val ExerciseUnitCount = data.getStringExtra(EXERCISE_UNIT_COUNT)!!.toFloat()
//                exerciseListViewModel.insertExercise(ExerciseName, ExerciseDescription, ExerciseReps, ExerciseSets, ExerciseUnitType!!, ExerciseUnitCount)
//
//            }
//        }
//    }
}