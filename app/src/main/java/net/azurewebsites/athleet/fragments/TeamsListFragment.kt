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
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.models.Team
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_workouts_list.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.*
import net.azurewebsites.athleet.Teams.*
import net.azurewebsites.athleet.getFirebaseTokenId
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamsListFragment : Fragment() {
    private val api = Api.createSafe()
    private val teamsListViewModel by viewModels<TeamsListViewModel> { TeamsListViewModelFactory(requireContext()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fab: View

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        val teamsAdapter = TeamsListAdapter { team -> adapterOnClick(team) }

        getTeams()

        recyclerView_Workout?.layoutManager = linearLayoutManager
        recyclerView_Workout?.adapter = teamsAdapter
        teamsAdapter.submitList(TeamsList(resources))

        // Button goes to Create New Team activity.
        fab = requireActivity().findViewById(R.id.fab)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTeams() {
        val callGetWorkouts = api.listTeams(getFirebaseTokenId())

        callGetWorkouts.enqueue(object: Callback<List<Team>> {
            override fun onResponse(call: Call<List<Team>>, response: Response<List<Team>>) {
                if(response.isSuccessful) {
                    teamsListViewModel.insertTeams(response.body()!!.toList())
                }
            }
            override fun onFailure(call: Call<List<Team>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        linearLayoutManager = LinearLayoutManager(context)
        val teamsAdapter = TeamsListAdapter { team -> adapterOnClick(team) }

        // workoutListViewModel.workoutsLiveData.observe(this.viewLifecycleOwner , { it.let { if(workoutListViewModel.workoutsLiveData.value!!.size != 0) workoutAdapter.submitList(it as MutableList<Workout>) } })
        teamsListViewModel.teamsLiveData.observe(this.viewLifecycleOwner , { it?.let { if(teamsListViewModel.teamsLiveData.value!!.size != 0) teamsAdapter.submitList(it as MutableList<Team>) } })
        val rootView = inflater!!.inflate(R.layout.fragment_teams_list, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView_Teams) as RecyclerView

        fab.setOnClickListener { fabOnClick() }
        recyclerView.adapter = teamsAdapter
        recyclerView.layoutManager=linearLayoutManager

        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        getTeams()
        fab.setOnClickListener { fabOnClick() }
    }

    private fun adapterOnClick(team: Team) {
        val intent = Intent(requireContext(), TeamDetailActivity()::class.java)  // THIS WILL BECOME TeamDetailActivity()
        intent.putExtra(TEAM_NAME, team.teamName)
        intent.putExtra(TEAM_DESCRIPTION, team.teamDescription)
        startActivityForResult(intent, 2)
    }

    private fun fabOnClick() {
        val intent = Intent(this.requireActivity(), AddTeamActivity::class.java) // THIS WILL BECOME CreateWorkoutActivity()
        startActivityForResult(intent, 1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        //Inserts Team into viewModel. */
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val teamName = intentData?.getStringExtra(TEAM_NAME).toString()
            val teamDescription = intentData?.getStringExtra(TEAM_DESCRIPTION).toString()

            val callCreateTeam = api.createTeam(getFirebaseTokenId(), teamName, teamDescription)
            callCreateTeam.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful) {
                        Toast.makeText(activity, "Created team", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                    }
                })
        }
        if(requestCode == 58) {
            Toast.makeText(activity, "Successfully deleted Team", Toast.LENGTH_LONG).show()
            /*val api: Api = Api.createSafe()
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

        else if(resultCode == 59) {
            Toast.makeText(activity, "Successfully deleted Team", Toast.LENGTH_LONG).show()
            /*val api: Api = Api.createSafe()
            FirebaseAuth.getInstance().currentUser?.getIdToken(false)
                ?.addOnCompleteListener { response ->
                    if (response.isSuccessful) {
                        val teamName = activity?.intent?.getStringExtra(TEAM_NAME).toString()
                        val call = api.deleteTeam(response.result?.token.toString(), teamName)

                        call.enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(
                                call: retrofit2.Call<ResponseBody>,
                                t: Throwable
                            ) {
                                Toast.makeText(activity, "Failed to leave Team", Toast.LENGTH_LONG)
                                    .show()
                            }

                            override fun onResponse(
                                call: retrofit2.Call<ResponseBody>,
                                response: retrofit2.Response<ResponseBody>
                            ) {
                                Toast.makeText(
                                    activity,
                                    "Successfully left Team",
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