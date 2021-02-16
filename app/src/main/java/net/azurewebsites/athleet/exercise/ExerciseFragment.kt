package net.azurewebsites.athleet.exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.databinding.FragmentExerciseBinding
import net.azurewebsites.athleet.network.Exercise


class ExerciseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = FragmentExerciseBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val exercise: Exercise =  ExerciseFragmentArgs.fromBundle(requireArguments()).selectedExercise
        val viewModelFactory = ExerciseViewModelFactory(exercise, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(ExerciseViewModel::class.java)


        return binding.root
    }

}