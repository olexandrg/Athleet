package net.azurewebsites.athleet.Teams
import android.content.Intent
import android.graphics.Color
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
import net.azurewebsites.athleet.chat.Message
import net.azurewebsites.athleet.chat.MessageType
import net.azurewebsites.athleet.databinding.FragmentTeamAdminBinding
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.Conversation
import net.azurewebsites.athleet.models.TeamInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamAdminFragment : Fragment() {
    // username, associated isAdmin flag
    private var teamList = ArrayList<Pair<String, Boolean>>()
    private lateinit var messages: List<Conversation>

    private lateinit var userName: String
    private lateinit var binding:FragmentTeamAdminBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentTeamAdminBinding>(inflater,
            R.layout.fragment_team_admin, container, false )

        val currentUserUserName = FirebaseAuth.getInstance().currentUser!!.displayName!!
        userName = currentUserUserName

        binding.buttonMakeUserAdmin.setOnClickListener {
            if (isAdmin(currentUserUserName)) {
                val desiredUserToBeAdmin = binding.editTextMakeUserAdmin.text.toString()
                updateUserToAdmin(desiredUserToBeAdmin)

                activity?.setResult(57)
                activity?.finish()
            }
        }

        getTeamUsers()

        binding.buttonDeleteTeam.setOnClickListener {
            deleteTeam()
            // Need to redirect to Users Dashboard after this.
        }

        binding.buttonLeaveTeam.setOnClickListener {
            leaveTeam()
            // Need to redirect to Users Dashboard after this.
        }

        binding.buttonModerate.setOnClickListener {
            moderateTeam()
        }

        return binding.root
    }

    private fun leaveTeam() {
        val intent = Intent()
        Log.e("thing", requireActivity().intent?.getStringExtra(TEAM_NAME).toString())
        intent.putExtra(TEAM_NAME, requireActivity().intent?.getStringExtra(TEAM_NAME).toString())
        activity?.setResult(59, intent)
        activity?.finish()
    }

    private fun deleteTeam() {
        val intent = Intent()
        intent.putExtra(TEAM_NAME, requireActivity().intent?.getStringExtra(TEAM_NAME).toString())
        Toast.makeText(activity, "Successfully Deleted Team (admin)", Toast.LENGTH_LONG).show()
        activity?.setResult(58, intent)
        activity?.finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun moderateTeam() {
        if (isAdmin(userName))
        {
            val teamName = requireActivity().intent?.getStringExtra(TEAM_NAME).toString()
            val apiCall = Api.createSafe().getTeamConversation(getFirebaseTokenId(), teamName)
            apiCall.enqueue(object: Callback<List<Conversation>> {
                override fun onResponse(call: Call<List<Conversation>>, response: Response<List<Conversation>>) {
                    Toast.makeText(activity, "Gathering chat history...", Toast.LENGTH_LONG).show()
                    messages = response.body()!!
                }
                
                override fun onFailure(call: Call<List<Conversation>>, t: Throwable) {
                    Toast.makeText(activity, "Failed to load messages", Toast.LENGTH_LONG).show()
                }
            })
        }
        else
        {
            Toast.makeText(activity, "Only admins can moderate the chat!", Toast.LENGTH_LONG).show()
        }
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

                    //begin button checks
                    if(isAdmin(userName))
                    {
                        binding.buttonDeleteTeam.visibility = View.VISIBLE
                        var count = 0
                        for (user in teamList) {
                            if (user.second)
                                count++
                            if (count >= 2) {
                                binding.buttonLeaveTeam.isEnabled = true
                                binding.buttonLeaveTeam.setBackgroundColor(Color.RED)
                                break
                            }
                        }
                    }
                    else
                    {
                        binding.buttonLeaveTeam.isEnabled = true
                        binding.buttonLeaveTeam.setBackgroundColor(Color.RED)
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
        for (teamUser in teamList) {
            if (userName == teamUser.first && teamUser.second) { return true }
        }
        return false
    }
}