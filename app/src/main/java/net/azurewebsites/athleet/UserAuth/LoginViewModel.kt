package net.azurewebsites.athleet.UserAuth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import net.azurewebsites.athleet.FirebaseUserLiveData
//import androidx.preference.PreferenceManager

class LoginViewModel : ViewModel() {

    companion object {

    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}
