package net.azurewebsites.athleet.Dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.databinding.adapters.ListenerUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_workouts_list.*
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Workouts.Workout
import net.azurewebsites.athleet.Workouts.WorkoutDetailActivity
import net.azurewebsites.athleet.Workouts.WorkoutListAdapter
import net.azurewebsites.athleet.fragments.TeamsListFragment
import net.azurewebsites.athleet.fragments.WorkoutsListFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import net.azurewebsites.athleet.Teams.TeamsListAdapter
import java.util.ArrayList


class DashboardActivity : AppCompatActivity() {
    private val newWorkoutActivityRequestCode = 1
    val workoutListViewModel by viewModels<WorkoutsListViewModel> { WorkoutsListViewModelFactory(this) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setUpTabs()
        linearLayoutManager = LinearLayoutManager(this)
//        tabLayout.setOnClickListener { if(tabLayout.selectedTabPosition == 0) {recyclerView.swapAdapter(WorkoutListAdapter({workout -> adapterOnClick(workout) }),true)} }
//        val workoutAdapter = WorkoutListAdapter { workout -> adapterOnClick(workout) }
//        recyclerView.adapter = workoutAdapter
//        workoutListViewModel.workoutsLiveData.observe(this , { it?.let { workoutAdapter.submitList(it as MutableList<Workout>) } })

/*        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }*/
    }
    private fun adapterOnClick(Workout: Workout) {
        val intent = Intent(this, WorkoutDetailActivity()::class.java)
        intent.putExtra(WORKOUT_NAME, Workout.name)
        startActivity(intent)
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
    private fun pageChanged(){return}
}
class ViewPagerAdapter(supportFragmentManager: FragmentManager):FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
    private var mFragmentList = ArrayList<Fragment>();
    private var mFragmentTitleList = ArrayList<String>();

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return  mFragmentList[position]
    }
    fun addFragment(fragment:Fragment,title:String){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

}

