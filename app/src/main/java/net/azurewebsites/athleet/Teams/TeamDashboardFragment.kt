package net.azurewebsites.athleet.Teams

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_team_dashboard.view.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.InviteTeamUser
import net.azurewebsites.athleet.MessagingActivity
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.*
import net.azurewebsites.athleet.user.OtherUserProfilePageActivity
import net.azurewebsites.athleet.user.UserProfilePageActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.time.LocalDate
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.hours
import kotlin.time.seconds

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
    private lateinit var currentUser:UserItem

    private lateinit var teamChatButton: Button

    @ExperimentalTime
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding =inflater.inflate(R.layout.fragment_team_dashboard, container, false )
        teamName = requireActivity().intent?.getStringExtra(TEAM_NAME)!!
        binding.textView_teamName.text = teamName
        val recyclerView: RecyclerView = binding.teamMemberListView
        fab = binding.fab
        teamChatButton = binding.button
        fab.setOnClickListener { fabOnClick() }
        teamChatButton.setOnClickListener {  onTeamChatButtonClick() }
        teamMemberListAdapter = TeamMemberListAdapter { teamMember-> adapterOnClick(teamMember) }
        teamMembersListViewModel.teamMembersLiveData.observe(this.viewLifecycleOwner , {
            it.let {
                if(teamMembersListViewModel.teamMembersLiveData.value!!.size != 0)
                    teamMemberListAdapter.submitList(it as MutableList<TeamUser>)
            }
        })
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = teamMemberListAdapter
        recyclerView.layoutManager = linearLayoutManager
        getTeamInfo()
        getUser()
        checkForWarning()
        setHasOptionsMenu(true)
        return binding
    }

    @ExperimentalTime
    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkForWarning()
    {
        val apiCall = api.getWarnings(getFirebaseTokenId())
        apiCall.enqueue(object: Callback<Date> {
            override fun onResponse(call: Call<Date>, response: Response<Date>) {
                if (response.body() != null)
                {
                    var expiredDate = response.body()!!.time.seconds.inSeconds + 24 * 3600

                    if (Date().time.seconds.inSeconds <= expiredDate)
                    {
                        Toast.makeText(activity, "You have been warned for chat misuse!.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Date>, t: Throwable) {
                Toast.makeText(activity, "Failed getting the user's warnings.", Toast.LENGTH_LONG).show()
            }


        })
    }


    private fun fabOnClick() {
        val intent = Intent(this.requireActivity(), InviteTeamUser::class.java)
        intent.putExtra(TEAM_NAME, teamName)
        startActivityForResult(intent, 1)
    }

    override fun onResume() {
        super.onResume()
        fab.setOnClickListener { fabOnClick() }
    }
    private fun onTeamChatButtonClick() {

        val intent = Intent(this.requireActivity(), MessagingActivity::class.java)
        intent.putExtra(TEAM_NAME, teamName)
        intent.putExtra("roomName", teamName)
        intent.putExtra("userName", currentUser.userName)
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

    private fun adapterOnClick(teamMember: TeamUser) {
        var intent:Intent
        if(teamMember.userName != currentUser.userName)
            intent = Intent(requireActivity(), OtherUserProfilePageActivity()::class.java)
        else
            intent = Intent(requireActivity(), UserProfilePageActivity()::class.java)
        intent.putExtra("userName", teamMember.userName)

        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getTeamInfo()
    }
    private fun getUser() {
        val callGetUser = api.retrieveExistingUser(getFirebaseTokenId())
        callGetUser.enqueue(object: Callback<List<UserItem>> {
            override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>>) {
                if(response.isSuccessful) { currentUser = response.body()!![0] }
            }
            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTeamInfo()
    {
        if(!teamName.isBlank()){
            val apiCall = api.teamInfo(getFirebaseTokenId(),  teamName)
            apiCall.enqueue(object: Callback<TeamInfo>{
                override fun onResponse(call: Call<TeamInfo>, response: Response<TeamInfo>) { if (response.isSuccessful) { teamMemberListAdapter.submitList(response.body()!!.users); teamInfo = response.body()!! ; }}
                override fun onFailure(call: Call<TeamInfo>, t: Throwable) { Toast.makeText(requireContext(), "Failed getting the team users", Toast.LENGTH_LONG).show() }
            })
        }
    }
}
