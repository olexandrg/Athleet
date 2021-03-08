package net.azurewebsites.athleet.Dashboard

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_dashboard.*
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.fragments.TeamsListFragment
import net.azurewebsites.athleet.fragments.WorkoutsListFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.workouts.Workout
import net.azurewebsites.athleet.workouts.WorkoutsListViewModel
import net.azurewebsites.athleet.workouts.WorkoutsListViewModelFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.Charset
import java.util.ArrayList


class DashboardActivity : AppCompatActivity() {
    private val newWorkoutActivityRequestCode = 1
    private var userName = ""
    val workoutListViewModel by viewModels<WorkoutsListViewModel> { WorkoutsListViewModelFactory(this) }
    val api = Api.createSafe()
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setUpTabs()
        linearLayoutManager = LinearLayoutManager(this)
    }
    private fun setUpTabs()
    {
        var adapter = ViewPagerAdapter(this.supportFragmentManager)
        adapter.addFragment(WorkoutsListFragment(), "Workouts")
        adapter.addFragment(TeamsListFragment(), "Teams")
        viewPager.adapter = adapter;
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.weightlifting_icon)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.teams_tab_icon)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.personal_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserMenuData() {
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener { response ->
            if(response.isSuccessful) {
                var token = "Bearer " + response.result?.token.toString()
                val callGetUser = api.checkExistingUser(token)
                callGetUser.enqueue(object:Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(response.isSuccessful) {
                            var response1 = response.body()?.source()?.readString(charset = Charset.defaultCharset())
                            val userData = response1?.split("[", "]", "{", "}", ",",":")?.map { it.trim()}
                            userName = userData?.get(5).toString()
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Helper function to fetch current user data and convert to UserItem data instance

        getUserMenuData()
        when (item.itemId) {
            R.id.personalMenu -> Toast.makeText(this, userName, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)

    }
}

class ViewPagerAdapter(supportFragmentManager: FragmentManager):FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var mFragmentList = ArrayList<Fragment>();
    private var mFragmentTitleList = ArrayList<String>();
    override fun getCount(): Int { return mFragmentList.size }
    override fun getItem(position: Int): Fragment { return  mFragmentList[position] }
    fun addFragment(fragment:Fragment,title:String){ mFragmentList.add(fragment); mFragmentTitleList.add(title); }
}

