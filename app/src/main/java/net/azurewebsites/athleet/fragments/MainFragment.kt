package net.azurewebsites.athleet.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.*
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import net.azurewebsites.athleet.*
import net.azurewebsites.athleet.Dashboard.DashboardActivity
import net.azurewebsites.athleet.UserAuth.LoginViewModel
import net.azurewebsites.athleet.databinding.FragmentMainBinding


open class MainFragment : Fragment() {

    public companion object
    {
        const val TAG = "MainFragment"
        const val SIGN_IN_REQUEST_CODE = 1001
        const val DASHBOARD_REQUEST_CODE = 2002
        const val ADD_WORKOUT_REQUEST_CODE = 3003
        lateinit var tokenString:String;

        /*public fun getUserAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance();
        }*/
    }

    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
        binding.authButton.setOnClickListener{
            binding.authButton.setOnClickListener { launchSignInFlow()}
        }
        binding.btnDashboard.setOnClickListener {GoToDashboard()}
    }
    override fun onResume() {
        super.onResume()
    }
    private fun GoToDashboard() {
        val intent = Intent(this.requireContext(), DashboardActivity::class.java)
        startActivityForResult(intent, DASHBOARD_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK)
            { /*login succeeded->*/
                Log.i(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName} with uid: ${FirebaseAuth.getInstance().uid}");
            }
            else
            {/*login failed->*/
                Log.i(TAG, getString(R.string.log_msg_err));
                /*  If response is null, the user canceled the
                  sign-in flow using the back button. Otherwise, check
                  the error code and handle the error.*/}
        };
    }

    /**
     * Observes the authentication state and changes the UI accordingly.
     * If there is a logged in user: (1) show a logout button and (2) display their name.
     * If there is no logged in user: show a login button
     */
    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    binding.authButton.text = getString(R.string.logout_button_text)
                    binding.authButton.setOnClickListener {
                        getInstance().signOut(requireContext())
                    }
                    // binding.welcomeText.text = getFactWithPersonalization(factToDisplay)
                    binding.welcomeText.text =
                        "You are now signed in. Welcome back ${FirebaseAuth.getInstance().currentUser?.displayName}!"
                    binding.btnDashboard.isVisible = true;



                }
                else -> {
                    binding.authButton.text = getString(R.string.login_button_text)
                    binding.authButton.setOnClickListener { launchSignInFlow() }
                    //binding.welcomeText.text = factToDisplay
                    binding.welcomeText.text = getString(R.string.not_signed_in)
                    binding.btnDashboard.isVisible = false;
                }
            }
        })
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
            // This is where you can provide more ways for users to register and
            // sign in.
        )
        // Create and launch the sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE.
        startActivityForResult(
            getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }
}