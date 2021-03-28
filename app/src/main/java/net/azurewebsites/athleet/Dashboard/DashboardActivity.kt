package net.azurewebsites.athleet.Dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_dashboard.*
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.fragments.TeamsListFragment
import net.azurewebsites.athleet.fragments.WorkoutsListFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.user.UserProfilePageActivity
import net.azurewebsites.athleet.workouts.WorkoutsListViewModel
import net.azurewebsites.athleet.workouts.WorkoutsListViewModelFactory
import java.util.ArrayList

class DashboardActivity : AppCompatActivity() {
    private val newWorkoutActivityRequestCode = 1
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

    private fun goToUserProfilePage() {
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

