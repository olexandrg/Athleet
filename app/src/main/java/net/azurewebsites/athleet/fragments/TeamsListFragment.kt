package net.azurewebsites.athleet.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_dashboard.*
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Teams.TeamItem
import net.azurewebsites.athleet.Workouts.TeamsListAdapter
import net.azurewebsites.athleet.Workouts.Workout
import net.azurewebsites.athleet.Workouts.WorkoutDetailActivity

class TeamsListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //requireActivity().recyclerView.swapAdapter(TeamsListAdapter {team -> adapterOnClick(team)},true)
        return inflater.inflate(R.layout.fragment_teams_list, container, false)
    }
    private fun adapterOnClick(team: TeamItem) {
        val intent = Intent(requireContext(), WorkoutDetailActivity()::class.java)
        intent.putExtra(WORKOUT_NAME, team.teamName)
        startActivity(intent)
    }
}