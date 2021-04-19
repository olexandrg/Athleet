package net.azurewebsites.athleet.user

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.models.UserItem

@RequiresApi(Build.VERSION_CODES.O)
class OtherUserProfilePageActivity : AppCompatActivity() {
    // store user data locally
    val api = Api.createSafe()
    private lateinit var user: UserItem
    private lateinit var teamMemberUsername : String
    private lateinit var usernameField:TextInputEditText
    private lateinit var headlineField:TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_user_detail_page)
        // populate menu with retrieved user data
        // getUserMenuData()
        // setupEnterKeyListeners()
        teamMemberUsername = intent!!.extras?.get("userName").toString()
        usernameField = findViewById<TextInputEditText>(R.id.userName)
        headlineField = findViewById<TextInputEditText>(R.id.userHeadline)
        usernameField.setText(teamMemberUsername)
        headlineField.setText("< This will be the user's headline once we implement API calls to make them retrievable >")

    }

}