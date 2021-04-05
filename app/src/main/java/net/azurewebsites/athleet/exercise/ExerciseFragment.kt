package net.azurewebsites.athleet.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.FragmentExerciseBinding
import net.azurewebsites.athleet.models.Exercise


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


        val items = listOf("lbs", "km", "mi", "¥", "psi", "kCal", "Android")
        val adapter = ArrayAdapter(requireContext(), R.layout.measureunit_list_item, items)
        binding.exerciseDetailUnit.setAdapter(adapter)

        return binding.root
    }

}