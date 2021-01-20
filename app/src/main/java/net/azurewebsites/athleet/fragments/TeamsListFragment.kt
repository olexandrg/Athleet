package net.azurewebsites.athleet.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.WORKOUT_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Teams.TeamItem
import net.azurewebsites.athleet.Workouts.WorkoutDetailActivity
import retrofit2.Callback
import okhttp3.ResponseBody

class TeamsListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //requireActivity().recyclerView.swapAdapter(TeamsListAdapter {team -> adapterOnClick(team)},true)
        return inflater.inflate(R.layout.fragment_teams_list, container, false)
    }
    private fun adapterOnClick(team: TeamItem) {
        val intent = Intent(requireContext(), WorkoutDetailActivity()::class.java)
        intent.putExtra(WORKOUT_NAME, team.teamName)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val api: Api = Api.createSafe("https://testapi.athleetapi.club/api/")
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener { response ->
            if(response.isSuccessful) {
                //ask taylor how to get the team name
                val teamName = data?.getStringExtra(WORKOUT_NAME).toString()
                val call = api.deleteTeam(response.result?.token.toString(), teamName)
                call.enqueue(object: Callback<ResponseBody> {
                    override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(activity, "Failed to delete Team", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: retrofit2.Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                        Toast.makeText(activity, "Successfully deleted Team", Toast.LENGTH_LONG).show()
                    }

                })
            }
            else {
                Toast.makeText(activity, "Couldn't get user token", Toast.LENGTH_LONG).show()
            }
        }
    }
}