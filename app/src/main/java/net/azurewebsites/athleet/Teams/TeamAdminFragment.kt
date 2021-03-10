package net.azurewebsites.athleet.Teams

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.ApiLib.TeamInfo
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.FragmentTeamAdminBinding
import net.azurewebsites.athleet.getFirebaseTokenId
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamAdminFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTeamAdminBinding>(inflater,
            R.layout.fragment_team_admin, container, false )
        val userName = FirebaseAuth.getInstance().currentUser!!.displayName;



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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUserToAdmin(userName : String) {
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener() { response ->
            if (response.isSuccessful) {
                val api = Api.createSafe()
                val apiCall = api.makeTeamUserCoach(getFirebaseTokenId(), TEAM_NAME, userName, true)
                val responseCode = apiCall.execute().code()
                if (!responseCode.equals(200)) {Toast.makeText(activity, "Failed finding user name. User may not be part of team.", Toast.LENGTH_LONG).show()}
            }
            else { Toast.makeText(activity, "Failed getting token of user.", Toast.LENGTH_LONG).show() }
        }
    }
}