package net.azurewebsites.athleet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.UserAuth.WorkoutsListViewModel
import net.azurewebsites.athleet.databinding.FragmentMainBinding


class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val workoutsListViewModel by viewModels<WorkoutsListViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater, R.layout.activity_dashboard, container, false);

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
    companion object {
        private lateinit var tokenString:String
    }
}