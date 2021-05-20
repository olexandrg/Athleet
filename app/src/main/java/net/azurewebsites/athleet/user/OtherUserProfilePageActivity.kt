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
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.DataSource
import net.azurewebsites.athleet.models.UserItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class OtherUserProfilePageActivity : AppCompatActivity() {
    val api = Api.createSafe()
    private val activity = this;
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun blockUserButtonOnClick() {
        if (dataSource.blockedUsersList.contains(teamMemberUsername))
        {
            Toast.makeText(this, "Unblocking user: "+ teamMemberUsername + ". Please wait...", Toast.LENGTH_SHORT).show();
            api.unblockUser(getFirebaseTokenId(), teamMemberUsername).enqueue(object:
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful) {
                        api.retrieveBlockedUsers(getFirebaseTokenId()).enqueue(object:
                            Callback<List<String>> {
                            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                                if(response.isSuccessful) {
                                    dataSource.setBlockList(response.body()!!.toMutableList());
                                    blockUserButton.setText("Block User");
                                    Toast.makeText(activity,"Successfully unblocked user: " + teamMemberUsername,Toast.LENGTH_SHORT).show()  }
                            }
                            override fun onFailure(call: Call<List<String>>, t: Throwable) { TODO("Not yet implemented") }
                        })}
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) { TODO("Not yet implemented") }
            })
        }
        else
        {
            Toast.makeText(this, "Blocking user: "+teamMemberUsername +". Please wait...", Toast.LENGTH_SHORT).show();
            api.blockUser(getFirebaseTokenId(), teamMemberUsername).enqueue(object:
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful) {
                        api.retrieveBlockedUsers(getFirebaseTokenId()).enqueue(object:
                            Callback<List<String>> {
                            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                                if(response.isSuccessful) {
                                    dataSource.setBlockList(response.body()!!.toMutableList());
                                    blockUserButton.setText("Unblock User");
                                    Toast.makeText(activity,"Successfully Blocked user: " + teamMemberUsername,Toast.LENGTH_SHORT).show()  }
                            }
                            override fun onFailure(call: Call<List<String>>, t: Throwable) { TODO("Not yet implemented") }
                        })}                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) { TODO("Not yet implemented") }
            })
        }
    }

}