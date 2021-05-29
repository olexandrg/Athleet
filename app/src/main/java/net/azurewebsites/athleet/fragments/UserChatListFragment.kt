package net.azurewebsites.athleet.fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.chat_into_notification.view.*
import kotlinx.android.synthetic.main.fragment_chat_list.*
import kotlinx.android.synthetic.main.fragment_workouts_list.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.AddChatActivity
import net.azurewebsites.athleet.Dashboard.AddTeamActivity
import net.azurewebsites.athleet.Dashboard.TEAM_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Teams.*
import net.azurewebsites.athleet.UserMessagingActivity
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.Conversation
import net.azurewebsites.athleet.models.Team
import net.azurewebsites.athleet.models.UserConvs
import net.azurewebsites.athleet.models.UserItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserChatListFragment : Fragment() {
    private val api = Api.createSafe()
    private val teamsListViewModel by viewModels<ChatListViewModel> { ChatListViewModelFactory(requireContext()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fab: View
    private lateinit var currentUser:UserItem

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        val teamsAdapter = ChatListAdapter { team -> adapterOnClick(team) }

        getTeams()

        recyclerView_Chats?.layoutManager = linearLayoutManager
        recyclerView_Chats?.adapter = teamsAdapter
        teamsAdapter.submitList(ChatList(resources))
        getUser()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTeams() {
        val callGetWorkouts = api.getUserConvList(getFirebaseTokenId())

        callGetWorkouts.enqueue(object: Callback<List<UserConvs>> {
            override fun onResponse(call: Call<List<UserConvs>>, response: Response<List<UserConvs>>) {
                if(response.isSuccessful) {
                    teamsListViewModel.insertChat(response.body()!!.toList())
                }
            }
            override fun onFailure(call: Call<List<UserConvs>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        linearLayoutManager = LinearLayoutManager(context)
        val teamsAdapter = ChatListAdapter { team -> adapterOnClick(team) }

        teamsListViewModel.chatLiveData.observe(this.viewLifecycleOwner) {
            if(teamsListViewModel.chatLiveData.value!!.count() != 0 && !teamsListViewModel.chatLiveData.value.isNullOrEmpty()) {
                it.let { teamsAdapter.submitList(it as MutableList<UserConvs>) }
            }
        }
        val rootView = inflater!!.inflate(R.layout.fragment_chat_list, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView_Chats) as RecyclerView

        fab = requireActivity().findViewById(R.id.fab)
        fab.setOnClickListener { fabOnClick() }
        recyclerView.adapter = teamsAdapter
        recyclerView.layoutManager=linearLayoutManager

        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        getTeams()
        fab.setOnClickListener { fabOnClick() }
    }

    private fun adapterOnClick(team: UserConvs) {
        //TODO: fix to Chatactivity of some kind
        val intent = Intent(requireContext(), UserMessagingActivity()::class.java)
        intent.putExtra(TEAM_DESCRIPTION, team.conversationID)
        intent.putExtra("userName", currentUser.userName)
        startActivityForResult(intent, 2)
    }

    private fun getUser() {
        val callGetUser = api.retrieveExistingUser(getFirebaseTokenId())
        callGetUser.enqueue(object: Callback<List<UserItem>> {
            override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>>) {
                if(response.isSuccessful) { currentUser = response.body()!![0] }
            }
            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun fabOnClick() {
        val intent = Intent(this.requireActivity(), AddChatActivity::class.java) // THIS WILL BECOME CreateWorkoutActivity()
        startActivityForResult(intent, 1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        //Inserts Team into viewModel. */
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val teamName = intentData?.getStringExtra(TEAM_NAME).toString()

            val callCreateTeam = api.CreateUserConv(getFirebaseTokenId(), teamName)
            callCreateTeam.enqueue(object : Callback<Int> {
                override fun onResponse(
                    call: Call<Int>,
                    response: Response<Int>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(activity, "Created Conversation", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}