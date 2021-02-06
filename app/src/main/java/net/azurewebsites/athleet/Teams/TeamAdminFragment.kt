package net.azurewebsites.athleet.Teams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.ApiLib.Api
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

        val api: Api = Api.createSafe("https://testapi.athleetapi.club/api/")
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
            }
        activity?.finish()
    }

    private fun deleteTeam() {
        Toast.makeText(activity, "Successfully Deleted Team (admin)", Toast.LENGTH_LONG).show()
        activity?.finishActivity(58)
        activity?.finish()
    }

}