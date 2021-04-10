package net.azurewebsites.athleet.Teams

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_team_admin.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.FragmentTeamAdminBinding
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.TeamInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamAdminFragment : Fragment() {
    // username, isAdmin
    private var teamList = ArrayList<Pair<String, Boolean>>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTeamAdminBinding>(inflater,
            R.layout.fragment_team_admin, container, false )

        val currentUserUserName = FirebaseAuth.getInstance().currentUser!!.displayName!!

        binding.buttonMakeUserAdmin.setOnClickListener {
            if (isAdmin(currentUserUserName)) {
                val desiredUserToBeAdmin = binding.editTextMakeUserAdmin.text.toString()
                updateUserToAdmin(desiredUserToBeAdmin)

                activity?.setResult(57)
                activity?.finish()
            }
        }

        getTeamUsers()

        if (!isAdmin(currentUserUserName)) {
            binding.buttonLeaveTeam.isEnabled = true
        }
        else {
            var count = 0
            for (user in teamList) {
                if (user.second)
                    count++
                if (count >= 2) {
                    binding.buttonLeaveTeam.isEnabled = true
                    break
                }
            }
        }

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
        var intent = Intent()
        Log.e("thing", requireActivity().intent?.getStringExtra(TEAM_NAME).toString())
        intent.putExtra(TEAM_NAME, requireActivity().intent?.getStringExtra(TEAM_NAME).toString())
        activity?.setResult(59, intent)
        activity?.finish()
    }

    private fun deleteTeam() {
        Toast.makeText(activity, "Successfully Deleted Team (admin)", Toast.LENGTH_LONG).show()
        activity?.finishActivity(58)
        activity?.finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUserToAdmin(userName : String) {
        val teamName = requireActivity()?.intent?.getStringExtra("name")!!
        val api = Api.createSafe()
        val apiCall = api.makeTeamUserCoach(getFirebaseTokenId(), teamName, userName, true)
        apiCall.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Toast.makeText(activity, "User is now admin.", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, "Failed finding user name. User may not be part of team.", Toast.LENGTH_LONG).show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTeamUsers()
    {
        val teamName = requireActivity()?.intent?.getStringExtra("name")!!
        val api = Api.createSafe()
        val apiCall = api.teamInfo(getFirebaseTokenId(), teamName)
        apiCall.enqueue(object: Callback<TeamInfo>{
            override fun onResponse(call: Call<TeamInfo>, response: Response<TeamInfo>) {
                if (response.isSuccessful) {
                    var userList = response.body()!!.users
                    for (user in userList) {
                        teamList.add(Pair(user.userName, user.isAdmin))
                    }
                }
            }
            override fun onFailure(call: Call<TeamInfo>, t: Throwable) {
                Toast.makeText(activity, "Failed getting the team users", Toast.LENGTH_LONG).show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isAdmin(userName: String) : Boolean {
        getTeamUsers()
        for (teamUser in teamList) {
            if (userName == teamUser.first && teamUser.second) { return true }
        }
        return false
    }
}