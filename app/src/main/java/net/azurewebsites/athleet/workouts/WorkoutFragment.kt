package net.azurewebsites.athleet.workouts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
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

        binding.workoutName.text = requireActivity().intent.extras?.getString(WORKOUT_NAME).toString()

        return binding.root
    }

}