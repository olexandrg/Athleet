package net.azurewebsites.athleet.Dashboard

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
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
import net.azurewebsites.athleet.user.UserProfilePageActivity
import net.azurewebsites.athleet.workouts.WorkoutDetailActivity
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
    private var userHeadline = ""
    private var userEmail = FirebaseAuth.getInstance().currentUser?.email
    val workoutListViewModel by viewModels<WorkoutsListViewModel> { WorkoutsListViewModelFactory(this) }
    val api = Api.createSafe()
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setUpTabs()
        findViewById<ImageView>(R.id.imageView_userProfilePicture).setOnClickListener { goToUserProfilePage() }
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
                            userHeadline = userData?.get(9).toString()
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
//        when (item.itemId) {
//            //R.id.personalMenu -> Toast.makeText(this, userName+"\n"+userHeadline+"\n"+userEmail, Toast.LENGTH_LONG).show()
//            //TODO: launch activity
//        }
        return super.onOptionsItemSelected(item)
    }

    fun goToUserProfilePage() {
        val intent = Intent(this, UserProfilePageActivity()::class.java)
        startActivity(intent)
    }
}

class ViewPagerAdapter(supportFragmentManager: FragmentManager):FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var mFragmentList = ArrayList<Fragment>();
    private var mFragmentTitleList = ArrayList<String>();
    override fun getCount(): Int { return mFragmentList.size }
    override fun getItem(position: Int): Fragment { return  mFragmentList[position] }
    fun addFragment(fragment:Fragment,title:String){ mFragmentList.add(fragment); mFragmentTitleList.add(title); }
}

