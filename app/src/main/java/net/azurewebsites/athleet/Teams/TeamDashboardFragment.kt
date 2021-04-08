package net.azurewebsites.athleet.Teams

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_team_dashboard.view.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.InviteTeamUser
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.DataSource
import net.azurewebsites.athleet.models.TeamInfo
import net.azurewebsites.athleet.models.TeamUser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class TeamDashboardFragment : Fragment() {
    private val api = Api.createSafe()
    private var teamName : String = ""
    private val teamMembersListViewModel by viewModels<TeamMembersListViewModel> { TeamMembersListViewModelFactory(requireContext()) }
    private lateinit var teamMemberListAdapter : TeamMemberListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fab: View
    private lateinit var dataSource:DataSource
    private lateinit var teamInfo: TeamInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =inflater.inflate(R.layout.fragment_team_dashboard, container, false )
        teamName = requireActivity().intent?.getStringExtra(TEAM_NAME)!!
        binding.textView_teamName.text = teamName
        val recyclerView: RecyclerView = binding.teamMemberListView
        fab = binding.fab
        fab.setOnClickListener { fabOnClick() }
        teamMemberListAdapter = TeamMemberListAdapter { teamMember-> adapterOnClick(teamMember) }
        teamMembersListViewModel.teamMembersLiveData.observe(this.viewLifecycleOwner , { it.let { if(teamMembersListViewModel.teamMembersLiveData.value!!.size != 0) teamMemberListAdapter.submitList(it as MutableList<TeamUser>) } })
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = teamMemberListAdapter
        recyclerView.layoutManager = linearLayoutManager
        getTeamInfo()
        setHasOptionsMenu(true)
        return binding
    }

    private fun fabOnClick() {
        val intent = Intent(this.requireActivity(), InviteTeamUser::class.java)
        intent.putExtra(TEAM_NAME, teamName)
        startActivityForResult(intent, 1)
    }

    override fun onResume() {
        super.onResume()
        getTeamInfo()
        fab.setOnClickListener { fabOnClick() }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.team_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title == "Team Admin")
        {
            val intent = Intent(requireActivity(), TeamAdminActivity::class.java)
            intent.putExtra(TEAM_NAME, teamName)
            startActivity(intent)
        }
        return false;
    }

    private fun adapterOnClick(teamMember: TeamUser) {
        Toast.makeText(activity, "Opening user page...", Toast.LENGTH_LONG).show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == UPDATED_ADMINS_STATUS)
            api.makeTeamUserCoach(getFirebaseTokenId(), data!!.getStringExtra("TeamToPromoteWithin")!!, data!!.getStringExtra("UserToPromote")!!,true)
                .enqueue(object: Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) { if (response.isSuccessful) { getTeamInfo()}}
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) { Toast.makeText( context, "Failed getting the team users", Toast.LENGTH_LONG).show() } }
                    )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTeamInfo()
    {
        if(!teamName.isBlank()){
            val apiCall = api.teamInfo(getFirebaseTokenId(),  teamName)
            apiCall.enqueue(object: Callback<TeamInfo>{
                override fun onResponse(call: Call<TeamInfo>, response: Response<TeamInfo>) { if (response.isSuccessful) { teamMembersListViewModel.insertTeamMembers(response.body()!!.users); teamInfo = response.body()!! ; }}
                override fun onFailure(call: Call<TeamInfo>, t: Throwable) { Toast.makeText(activity, "Failed getting the team users", Toast.LENGTH_LONG).show() }
            })
        }

    }
}