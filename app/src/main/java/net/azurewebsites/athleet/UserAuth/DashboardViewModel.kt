package net.azurewebsites.athleet.UserAuth

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.withContext
import net.azurewebsites.athleet.FirebaseUserLiveData
import net.azurewebsites.athleet.databinding.FragmentMainBinding
import net.azurewebsites.athleet.fragments.MainFragment

//import androidx.preference.PreferenceManager

class DashboardViewModel : ViewModel(){
    companion object{
        lateinit var userAccountData:FirebaseAuth;
    }
    private lateinit var binding: FragmentMainBinding

    fun populateUserInfo(){

        //        FirebaseAuth.getInstance().currentUser.displayName
    }


}
