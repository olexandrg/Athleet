/*
package net.azurewebsites.athleet.Dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

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

}*/
