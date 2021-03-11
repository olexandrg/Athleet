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
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.DashboardActivity
import net.azurewebsites.athleet.UserAuth.LoginViewModel
import net.azurewebsites.athleet.databinding.FragmentMainBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class MainFragment : Fragment() {
    private val api = Api.createSafe()
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentMainBinding

    companion object
    {
        const val TAG = "MainFragment"
        const val SIGN_IN_REQUEST_CODE = 1001
        const val DASHBOARD_REQUEST_CODE = 2002
    }

    // Get a reference to the ViewModel scoped to this Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
        binding.authButton.setOnClickListener{
            binding.authButton.setOnClickListener { launchSignInFlow()}
        }
        binding.btnDashboard.setOnClickListener {goToDashboard()}
    }

    private fun goToDashboard() {
        val intent = Intent(this.requireContext(), DashboardActivity::class.java)
        startActivityForResult(intent, DASHBOARD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)

            //login succeeded
            if (resultCode == Activity.RESULT_OK)   {
                Log.
                    i(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().
                    currentUser?.displayName} with uid: ${FirebaseAuth.getInstance().uid}");
                FirebaseAuth.getInstance().
                    currentUser?.getIdToken(false)?.
                    addOnCompleteListener { response ->
                        if(response.isSuccessful) {
                            val userName = FirebaseAuth.getInstance().currentUser!!.displayName
                            val call = api.addNewUser("Bearer " + response.result?.token.toString(),userName!!, "Welcome back, "+userName )

                            call.enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(
                                    call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
                                ) {
                                    if(response.isSuccessful)
                                        binding.btnDashboard.isVisible = true;
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }
                            })
                        }
                    }
            }

            //  If response is null, the user canceled the
            //  sign-in flow using the back button. Otherwise, check
            //  the error code and handle the error.
            else {
                Log.i(TAG, getString(R.string.log_msg_err));
            }
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
                    binding.welcomeText.text =
                        "You are now signed in. Welcome back ${FirebaseAuth.getInstance().currentUser?.displayName}!"
                    FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener { response ->
                        if(response.isSuccessful) {
                            val userName = FirebaseAuth.getInstance().currentUser!!.displayName
                            val callCheckExisting = api.checkExistingUser("Bearer " + response.result?.token.toString())
                            val token = "Bearer " + response.result?.token.toString()
                            callCheckExisting.enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(
                                    call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
                                ) {
                                    when(response.code())
                                    {
                                        404-> {
                                        val call = api.addNewUser(token,userName!!, "Welcome back, "+userName )
                                        call.enqueue(object : Callback<ResponseBody> {
                                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                                    if(response.isSuccessful)
                                                        binding.btnDashboard.isVisible = true;
                                            }
                                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) { TODO("Not yet implemented") }
                                            })
                                        }
                                        200->{binding.btnDashboard.isVisible = true;}
                                    }

                                        binding.btnDashboard.isVisible = true;
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }

                            })

//                            val call = api.addNewUser("Bearer " + response.result?.token.toString(),userName!!, "Welcome back, "+userName )
//                            call.enqueue(object : Callback<ResponseBody> {
//                                override fun onResponse(
//                                    call: Call<ResponseBody>,
//                                    response: Response<ResponseBody>
//                                ) {
//                                    if(response.isSuccessful)
//                                        binding.btnDashboard.isVisible = true;
//                                }
//
//                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                                    TODO("Not yet implemented")
//                                }

                            //})
                        }

                    }
                }
                else -> {
                    binding.authButton.text = getString(R.string.login_button_text)
                    binding.authButton.setOnClickListener { launchSignInFlow() }
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
            AuthUI.IdpConfig.EmailBuilder().build()
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