package net.azurewebsites.athleet.Teams

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.TeamMemberListAdapter
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.TEAM_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.InviteTeamUser
import net.azurewebsites.athleet.MessagingActivity
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.FragmentTeamDashboardBinding
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.Team
import net.azurewebsites.athleet.models.TeamInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamDashboardFragment : Fragment() {
    private var teamList = ArrayList<String>()
    private var teamName : String = ""

    private lateinit var teamMemberListAdapter : TeamMemberListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var teamChatButton: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTeamDashboardBinding>(inflater,
            R.layout.fragment_team_dashboard, container, false )

        teamName = requireActivity()?.intent?.getStringExtra("name")!!
        binding.textViewTeamName.text = teamName

        val recyclerView: RecyclerView = binding.teamMemberListView

        binding.fab.setOnClickListener {
            fabOnClick()
        }

        binding.button.setOnClickListener {
            onTeamChatButtonClick()
        }

        teamMemberListAdapter = TeamMemberListAdapter(teamList)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = teamMemberListAdapter
        recyclerView.layoutManager = linearLayoutManager
        getTeamUsers()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun fabOnClick() {
        val intent = Intent(this.requireActivity(), InviteTeamUser::class.java)
        intent.putExtra(TEAM_NAME, teamName)
        startActivityForResult(intent, 1)
    }

    private fun onTeamChatButtonClick() {
        val intent = Intent(this.requireActivity(), MessagingActivity::class.java)
        intent.putExtra(TEAM_NAME, teamName)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.team_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun adapterOnClick(team: Team) {
        val intent = Intent(requireContext(), TeamDetailActivity()::class.java)  // THIS WILL BECOME TeamDetailActivity()
        intent.putExtra(TEAM_NAME, team.teamName)
        intent.putExtra(TEAM_DESCRIPTION, team.teamDescription)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTeamUsers()
    {
        val api = Api.createSafe()
        val apiCall = api.teamInfo(getFirebaseTokenId(),  teamName)
        apiCall.enqueue(object: Callback<TeamInfo>{
            override fun onResponse(call: Call<TeamInfo>, response: Response<TeamInfo>) {
                if (response.isSuccessful) {
                    val userList = response.body()!!.users
                    for (user in userList) {
                        teamList.add(user.userName)
                    }

                    teamMemberListAdapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<TeamInfo>, t: Throwable) {
                Toast.makeText(activity, "Failed getting the team users", Toast.LENGTH_LONG).show()
            }
        })
    }
}