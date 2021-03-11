package net.azurewebsites.athleet.Teams

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.TeamMemberListAdapter
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.ApiLib.TeamInfo
import net.azurewebsites.athleet.Dashboard.TEAM_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.InviteTeamUser
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.FragmentTeamDashboardBinding
import net.azurewebsites.athleet.getFirebaseTokenId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamDashboardFragment : Fragment() {
    private var teamList = ArrayList<String>()
    private var teamName : String = ""

    private lateinit var teamMemberListAdapter : TeamMemberListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fab: View

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTeamDashboardBinding>(inflater,
            R.layout.fragment_team_dashboard, container, false )

        teamName = requireActivity()?.intent?.getStringExtra("name")!!

        val recyclerView: RecyclerView = binding.teamMemberListView

        binding.fab.setOnClickListener {
            fabOnClick()
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
        startActivityForResult(intent, 1)
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
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener() { response ->
            if (response.isSuccessful) {
                val api = Api.createSafe()
                val apiCall = api.teamInfo(getFirebaseTokenId(),  teamName)
                apiCall.enqueue(object: Callback<TeamInfo>{
                    override fun onResponse(call: Call<TeamInfo>, response: Response<TeamInfo>) {
                        if (response.isSuccessful) {
                            val userList = response.body()!!.users
                            for (user in userList) {
                                teamList.add(user.userName)
                            }
                        }
                    }
                    override fun onFailure(call: Call<TeamInfo>, t: Throwable) {
                        Toast.makeText(activity, "Failed getting the team users", Toast.LENGTH_LONG).show()
                    }
                })
            }
            else { Toast.makeText(activity, "Failed getting token of user", Toast.LENGTH_LONG).show() }
        }
    }
}