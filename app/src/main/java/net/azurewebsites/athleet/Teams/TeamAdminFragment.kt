package net.azurewebsites.athleet.Teams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_team_admin.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.ApiLib.TeamInfo
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.R
import retrofit2.Callback
import net.azurewebsites.athleet.databinding.FragmentTeamAdminBinding
import okhttp3.ResponseBody


class TeamAdminFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentTeamAdminBinding>(inflater,
            R.layout.fragment_team_admin, container, false )
        val userName = FirebaseAuth.getInstance().currentUser!!.displayName;
        //change true to false when the api call is used
        button_leaveTeam.isEnabled = true

        /*val api: Api = Api.createSafe("https://testapi.athleetapi.club/api/")
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)
            ?.addOnCompleteListener { response ->
                if (response.isSuccessful) {
                    val teamName = activity?.intent?.getStringExtra(TEAM_NAME).toString()
                    val call = api.teamInfo(response.result?.token.toString(), teamName)

                    call.enqueue(object : Callback<TeamInfo> {
                        override fun onFailure(
                            call: retrofit2.Call<TeamInfo>,
                            t: Throwable
                        ) {
                            Toast.makeText(activity, "Failed to get team information", Toast.LENGTH_LONG)
                                .show()
                        }

                        override fun onResponse(
                            call: retrofit2.Call<TeamInfo>,
                            response: retrofit2.Response<TeamInfo>
                        ) {
                            var users = response.body()!!.users
                            var isAdmin: Boolean = false;
                            var multAdmin: Int = 0;
                            for(user in users) {
                                if (user.isAdmin)
                                    multAdmin++;
                                if (user.UserName == userName)
                                    isAdmin = true
                            }
                            if((isAdmin && multAdmin > 2) || !isAdmin)
                                button_leaveTeam.isEnabled = true
                        }
                    })
                } else {
                    Toast.makeText(activity, "Couldn't get user token", Toast.LENGTH_LONG)
                        .show()
                }
            }*/

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
        activity?.setResult(59)
        activity?.finish()
    }

    private fun deleteTeam() {
        Toast.makeText(activity, "Successfully Deleted Team (admin)", Toast.LENGTH_LONG).show()
        activity?.finishActivity(58)
        activity?.finish()
    }

}