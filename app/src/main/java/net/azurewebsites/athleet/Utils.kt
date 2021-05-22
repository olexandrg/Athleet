package net.azurewebsites.athleet

import android.os.Build
import com.google.firebase.auth.FirebaseAuth

fun getFirebaseTokenId(): String {
    return "Bearer " + FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.result?.token.toString()
}