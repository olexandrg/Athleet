package net.azurewebsites.athleet.fragments

import android.content.Intent
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Teams.TeamItem
import retrofit2.Callback
import okhttp3.ResponseBody
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_workouts_list.*
import net.azurewebsites.athleet.Dashboard.*
import net.azurewebsites.athleet.Teams.*


class TeamsListFragment : Fragment() {
    private val teamsListViewModel by viewModels<TeamsListViewModel> { TeamsListViewModelFactory(requireContext()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fab: View
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        val teamsAdapter = TeamsListAdapter { team -> adapterOnClick(team) }
        recyclerView_Workout?.layoutManager = linearLayoutManager
        recyclerView_Workout?.adapter = teamsAdapter
        teamsAdapter.submitList(TeamsList(resources))

        // fab: floating action button. This button leads to adding the team window (activity_add_team.xml)
        fab = requireActivity().findViewById(R.id.fab)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        linearLayoutManager = LinearLayoutManager(context)
        val teamsAdapter = TeamsListAdapter { team -> adapterOnClick(team) }
        teamsListViewModel.teamsLiveData.observe(this.viewLifecycleOwner , { it?.let { teamsAdapter.submitList(it as MutableList<TeamItem>) } })
        val rootView = inflater!!.inflate(R.layout.fragment_teams_list, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView_Teams) as RecyclerView
        fab.setOnClickListener { fabOnClick() }
        recyclerView.adapter = teamsAdapter
        recyclerView.layoutManager=linearLayoutManager
        return rootView
    }
    override fun onResume() {
        super.onResume()
        fab.setOnClickListener { fabOnClick() }
    }
    private fun adapterOnClick(team: TeamItem) {
        val intent = Intent(requireContext(), TeamDetailActivity()::class.java)  // THIS WILL BECOME TeamDetailActivity()
        intent.putExtra(TEAM_NAME, team.teamName)
        intent.putExtra(TEAM_DESCRIPTION, team.teamDescription)
        startActivity(intent)
    }
    private fun fabOnClick() {
        val intent = Intent(this.requireActivity(), AddTeamActivity::class.java) // THIS WILL BECOME CreateWorkoutActivity()
        startActivityForResult(intent, 1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        //Inserts Team into viewModel. */
        // THIS WILL NEED TO BE CHANGE
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val TeamName = data.getStringExtra(WORKOUT_NAME)
                val WorkoutDescription = data.getStringExtra(WORKOUT_DESCRIPTION)
                teamsListViewModel.insertTeam(teamName = TeamName, teamDescription = WorkoutDescription)

            }
        }
        if(resultCode == 58) {
            Toast.makeText(activity, "Successfully deleted Team", Toast.LENGTH_LONG).show();
            /*val api: Api = Api.createSafe("https://testapi.athleetapi.club/api/")
            FirebaseAuth.getInstance().currentUser?.getIdToken(false)
                ?.addOnCompleteListener { response ->
                    if (response.isSuccessful) {
                        //ask taylor how to get the team name
                        val teamName = intentData?.getStringExtra(WORKOUT_NAME).toString()
                        val call = api.deleteTeam(response.result?.token.toString(), teamName)
                        call.enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(
                                call: retrofit2.Call<ResponseBody>,
                                t: Throwable
                            ) {
                                Toast.makeText(activity, "Failed to delete Team", Toast.LENGTH_LONG)
                                    .show()
                            }

                            override fun onResponse(
                                call: retrofit2.Call<ResponseBody>,
                                response: retrofit2.Response<ResponseBody>
                            ) {
                                Toast.makeText(
                                    activity,
                                    "Successfully deleted Team",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        })
                    } else {
                        Toast.makeText(activity, "Couldn't get user token", Toast.LENGTH_LONG)
                            .show()
                    }
                }*/
        }
    }
}