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
import kotlinx.android.synthetic.main.fragment_chat_list.*
import kotlinx.android.synthetic.main.fragment_workouts_list.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.AddTeamActivity
import net.azurewebsites.athleet.Dashboard.TEAM_DESCRIPTION
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Teams.*
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.Conversation
import net.azurewebsites.athleet.models.Team
import net.azurewebsites.athleet.models.UserConvs
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserChatListFragment : Fragment() {
    private val api = Api.createSafe()
    private val teamsListViewModel by viewModels<ChatListViewModel> { ChatListViewModelFactory(requireContext()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fab: View

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        val teamsAdapter = ChatListAdapter { team -> adapterOnClick(team) }

        getTeams()

        recyclerView_Chats?.layoutManager = linearLayoutManager
        recyclerView_Chats?.adapter = teamsAdapter
        teamsAdapter.submitList(ChatList(resources))
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
        val intent = Intent(requireContext(), TeamDetailActivity()::class.java)
        intent.putExtra(TEAM_NAME, team.UserName)
        intent.putExtra(TEAM_DESCRIPTION, team.ConversationID)
        startActivityForResult(intent, 2)
    }

    private fun fabOnClick() {
        //TODO: fix to chat create activity of some kind
        val intent = Intent(this.requireActivity(), AddTeamActivity::class.java) // THIS WILL BECOME CreateWorkoutActivity()
        startActivityForResult(intent, 1)
    }
}