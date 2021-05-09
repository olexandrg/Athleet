package net.azurewebsites.athleet.user

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.models.DataSource
import net.azurewebsites.athleet.models.UserItem

@RequiresApi(Build.VERSION_CODES.O)
class OtherUserProfilePageActivity : AppCompatActivity() {
    val api = Api.createSafe()
    private lateinit var user: UserItem
    private lateinit var teamMemberUsername : String
    private lateinit var usernameField:TextInputEditText
    private lateinit var headlineField:TextInputEditText
    private lateinit var sendMessageButton:Button
    private lateinit var blockUserButton:Button
    private lateinit var dataSource: DataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_user_detail_page)
        setShitUp()
    }

    private fun setShitUp() {
        dataSource = DataSource.getDataSource(resources)
        teamMemberUsername = intent!!.extras?.get("userName").toString()

        usernameField = findViewById(R.id.userName)
        headlineField = findViewById(R.id.userHeadline)

        blockUserButton = findViewById(R.id.block_user_button)
        blockUserButton.setOnClickListener { blockUserButtonOnClick() }

        if (dataSource.blockedUsersList.contains(teamMemberUsername))
            blockUserButton.setText("Unblock User")
        else
            blockUserButton.setText("Block User")


        sendMessageButton = findViewById(R.id.send_message_button)
        sendMessageButton.setOnClickListener{ Toast.makeText(this, "Opening direct message room...", Toast.LENGTH_SHORT).show() }

        usernameField.setText(teamMemberUsername)
        headlineField.setText("< This will be the user's headline once we implement API calls to make them retrievable >")
    }

    private fun blockUserButtonOnClick() {
        if (dataSource.blockedUsersList.contains(teamMemberUsername))
        {
            Toast.makeText(this, "Unblocked user: "+ teamMemberUsername, Toast.LENGTH_SHORT).show();
            dataSource.unblockUser(teamMemberUsername)
            blockUserButton.setText("Block User")
        }
        else
        {
            Toast.makeText(this, "Blocked user: "+teamMemberUsername, Toast.LENGTH_SHORT).show();
            dataSource.blockUser( teamMemberUsername)
            blockUserButton.setText("Unblock User")
        }
    }

}