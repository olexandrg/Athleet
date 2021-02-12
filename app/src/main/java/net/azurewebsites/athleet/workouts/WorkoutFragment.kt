package net.azurewebsites.athleet.workouts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
import net.azurewebsites.athleet.exercise.ExerciseListAdapter
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.FragmentWorkoutBinding


class WorkoutFragment : Fragment() {

    private val viewModel: WorkoutViewModel by lazy {
        ViewModelProvider(this).get(WorkoutViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentWorkoutBinding>(inflater,
            R.layout.fragment_workout, container, false )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.exercisesListRecView.adapter = ExerciseListAdapter(ExerciseListAdapter.OnClickListener {
            viewModel.displayExerciseDetails(it)
        })

        viewModel.navigateToSelectedExercise.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(
                    WorkoutFragmentDirections.actionWorkoutFragmentToExerciseFragment(it))
                viewModel.displayExerciseDetailsComplete()
            }
        })

        binding.workoutName.text = requireActivity().intent.extras?.getString(WORKOUT_NAME).toString()
        binding.fabAddExercise.setOnClickListener { view: View ->
            Log.i("WorkoutFragment", "Fab add exercise clicked")
            view.findNavController().navigate(R.id.action_workoutFragment_to_addExerciseActivity)
        }



        return binding.root
    }

}