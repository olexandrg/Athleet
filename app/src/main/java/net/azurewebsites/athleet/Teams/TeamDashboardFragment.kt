package net.azurewebsites.athleet.Teams

import android.os.Bundle
import android.view.*
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.TeamMemberListAdapter
import net.azurewebsites.athleet.Dashboard.TEAM_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.TeamMemberListView
import net.azurewebsites.athleet.databinding.FragmentTeamDashboardBinding

class TeamDashboardFragment : Fragment() {

    private lateinit var viewModel: TeamViewModel

    private val teamList = ArrayList<String>()
    private lateinit var teamMemberListAdapter : TeamMemberListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTeamDashboardBinding>(inflater,
            R.layout.fragment_team_dashboard, container, false )

        viewModel = ViewModelProvider(this).get(TeamViewModel()::class.java)

        binding.root.findViewById<TextView>(R.id.teamName).text = requireActivity().intent.extras?.getString(TEAM_NAME).toString()
        binding.root.findViewById<TextView>(R.id.teamDescription).text = requireActivity().intent.extras?.getString(TEAM_DESCRIPTION).toString()

        //viewModel.teamDescription = requireActivity().intent.extras?.getString(TEAM_DESCRIPTION).toString()
        val recyclerView: RecyclerView = binding.teamMemberListView
        teamMemberListAdapter = TeamMemberListAdapter(teamList)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = teamMemberListAdapter
        recyclerView.layoutManager = linearLayoutManager
        getData()

        setHasOptionsMenu(true)
        return binding.root
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

    fun getData()
    {
        teamList.add("Simeon")
        teamList.add("Nathan")
        teamList.add("Olex")
        teamList.add("Ryan")
        teamList.add("Taylor")
        teamMemberListAdapter.notifyDataSetChanged()
    }
}