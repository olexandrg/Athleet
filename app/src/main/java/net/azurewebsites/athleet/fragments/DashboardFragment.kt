package net.azurewebsites.athleet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.FirebaseUserLiveData
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.UserAuth.DashboardViewModel
import net.azurewebsites.athleet.databinding.FragmentMainBinding


class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {

    }
}