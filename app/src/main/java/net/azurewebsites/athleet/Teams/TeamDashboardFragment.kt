package net.azurewebsites.athleet.Teams

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import net.azurewebsites.athleet.Dashboard.TEAM_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.FragmentTeamDashboardBinding

class TeamDashboardFragment : Fragment() {

    private lateinit var viewModel: TeamViewModel

    val teamMembers = arrayOf<String>("Nathan", "Simeon", "Olex", "Taylor", "Ryan")

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
}