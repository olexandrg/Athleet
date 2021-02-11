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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
import net.azurewebsites.athleet.Exercises.*
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Teams.TeamViewModel
import net.azurewebsites.athleet.databinding.FragmentTeamDashboardBinding
import net.azurewebsites.athleet.databinding.FragmentWorkoutBinding


class WorkoutFragment : Fragment() {

    private lateinit var viewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentWorkoutBinding>(inflater,
            R.layout.fragment_workout, container, false )

        viewModel = ViewModelProvider(this).get(WorkoutViewModel()::class.java)

        binding.workoutName.text = requireActivity().intent.extras?.getString(WORKOUT_NAME).toString()
        binding.textApiResponse.text = viewModel.response.value

        return binding.root
    }

}