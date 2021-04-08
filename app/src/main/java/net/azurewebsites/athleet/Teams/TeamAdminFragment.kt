package net.azurewebsites.athleet.Teams

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.firebase.ui.auth.data.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.activity_user_detail.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.TEAM_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.databinding.FragmentTeamAdminBinding
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.TeamInfo
import net.azurewebsites.athleet.models.TeamUser
import net.azurewebsites.athleet.models.UserItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val UPDATED_ADMINS_STATUS = 6969
@RequiresApi(Build.VERSION_CODES.O)
class TeamAdminFragment : Fragment() {
    // username, isAdmin
    private val teamName = requireActivity().intent!!.getStringExtra(TEAM_NAME)!!
    private val api = Api.createSafe()

    private lateinit var userInput: EditText
    private lateinit var makeAdminButton:Button
    private lateinit var buttonLeaveTeam:Button
    private lateinit var buttonDeleteTeam:Button
    private lateinit var currentUser: UserItem
    private lateinit var currentTeam:TeamInfo


    private fun getTeam(){
        teamName
        val getTeamInfo = api.teamInfo(getFirebaseTokenId(), teamName)
        getTeamInfo.enqueue(object: Callback<TeamInfo> {
            override fun onResponse(call: Call<TeamInfo>, response: Response<TeamInfo>) { currentTeam = response.body()!! }
            override fun onFailure(call: Call<TeamInfo>, t: Throwable) { Toast.makeText(activity, "Failed finding user name. User may not be part of team.", Toast.LENGTH_LONG).show() }
        })
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
    private fun getCurrentUser(){
        val apiCall = api.retrieveExistingUser(getFirebaseTokenId())
        apiCall.enqueue(object: Callback<List<UserItem>> {
            override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>>) { currentUser=response.body()!![0] }
            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) { Toast.makeText(activity, "Failed finding user name. User may not be part of team.", Toast.LENGTH_LONG).show() }
        })
    }

    private fun updateUserToAdmin() {
        val apiCall = api.makeTeamUserCoach(getFirebaseTokenId(), currentTeam.teamName, userInput.text.toString(), true)
        apiCall.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(requireContext(), "User '" + userName + "' is now admin.", Toast.LENGTH_LONG).show()

            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed finding user name. User may not be part of team.", Toast.LENGTH_LONG).show()

            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCurrentUser()
        getTeam()

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_team_admin, container, false)
        userInput = rootView.findViewById(R.id.editText_makeUserAdmin)
        makeAdminButton = rootView.findViewById(R.id.button_makeUserAdmin)
        buttonDeleteTeam = rootView.findViewById(R.id.button_leaveTeam)
        buttonLeaveTeam = rootView.findViewById(R.id.button_deleteTeam)

        makeAdminButton.setOnClickListener { updateUserToAdmin(); }
        buttonDeleteTeam.setOnClickListener { deleteTeam() }
        buttonLeaveTeam.setOnClickListener { leaveTeam() }

        return rootView
    }


}