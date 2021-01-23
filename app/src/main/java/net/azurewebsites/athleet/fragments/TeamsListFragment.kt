package net.azurewebsites.athleet.fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_workouts_list.*
import net.azurewebsites.athleet.Dashboard.*
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Teams.*
import net.azurewebsites.athleet.Workouts.Workout
import net.azurewebsites.athleet.Workouts.WorkoutDetailActivity
import net.azurewebsites.athleet.Workouts.WorkoutList
import net.azurewebsites.athleet.Workouts.WorkoutListAdapter

class TeamsListFragment : Fragment() {
    private val teamsListViewModel by viewModels<TeamsListViewModel> { TeamsListViewModelFactory(requireContext()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        val teamsAdapter = TeamsListAdapter { team -> adapterOnClick(team) }
        recyclerView_Workout?.layoutManager = linearLayoutManager
        recyclerView_Workout?.adapter = teamsAdapter
        teamsAdapter.submitList(TeamsList(resources))
        val fab: View = requireActivity().findViewById(R.id.fab)
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
        //recyclerView_Workouts.layoutManager = linearLayoutManager
        val teamsAdapter = TeamsListAdapter { team -> adapterOnClick(team) }
        teamsListViewModel.teamsLiveData.observe(this.viewLifecycleOwner , { it?.let { teamsAdapter.submitList(it as MutableList<TeamItem>) } })
        val rootView = inflater!!.inflate(R.layout.fragment_teams_list, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView_Teams) as RecyclerView
        recyclerView.adapter = teamsAdapter
        recyclerView.layoutManager=linearLayoutManager
        return rootView
    }
    private fun adapterOnClick(team: TeamItem) {
        val intent = Intent(requireContext(), WorkoutDetailActivity()::class.java)
        intent.putExtra(WORKOUT_NAME, team.teamName)
        startActivity(intent)
    }
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
                val TeamName = data.getStringExtra(WORKOUT_NAME)
                val WorkoutDescription = data.getStringExtra(WORKOUT_DESCRIPTION)
                teamsListViewModel.insertTeam(teamName = TeamName, teamDescription = WorkoutDescription)

            }
        }
    }
}