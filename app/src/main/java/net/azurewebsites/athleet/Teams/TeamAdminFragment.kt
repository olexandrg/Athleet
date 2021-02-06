package net.azurewebsites.athleet.Teams

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.ActivityTeamDetailBinding
import net.azurewebsites.athleet.databinding.FragmentTeamAdminBinding


class TeamAdminFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentTeamAdminBinding>(inflater,
            R.layout.fragment_team_admin, container, false )

        binding.buttonDeleteTeam.setOnClickListener {
            deleteTeam()
            // Need to redirect to Users Dashboard after this.
        }

        binding.buttonLeaveTeam.setOnClickListener {
            leaveTeam()
            // Need to redirect to Users Dashboard after this.
        }

        return binding.root
    }

    private fun leaveTeam() {
        Toast.makeText(activity, "Successfully Left Team", Toast.LENGTH_LONG).show()
        activity?.finish()
    }

    private fun deleteTeam() {
        Toast.makeText(activity, "Successfully Deleted Team (admin)", Toast.LENGTH_LONG).show()
        activity?.finishActivity(58)
        activity?.finish()
    }

}