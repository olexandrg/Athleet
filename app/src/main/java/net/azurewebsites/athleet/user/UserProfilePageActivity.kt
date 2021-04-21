package net.azurewebsites.athleet.user

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.blocked_user_list_item.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.blockedUsersList
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.UserItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfilePageActivity : AppCompatActivity() {
    // store user data locally
    val api = Api.createSafe()
    val blockedUserListAdapter = BlockedUserListAdapter({ user->blockedUsersList.remove(user)})
    private lateinit var rv:RecyclerView
    private lateinit var user: UserItem
    private var userEmail = FirebaseAuth.getInstance().currentUser?.email
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        // populate menu with retrieved user data
        getUserMenuData()
        setupEnterKeyListeners()
        val emailField = findViewById<TextView>(R.id.userEmail)
        emailField.setText(userEmail)
        rv = findViewById(R.id.recyclerView_blockedUsers)
        rv.adapter = blockedUserListAdapter
        blockedUserListAdapter.submitList(blockedUsersList)
    }

    // retrieve user data
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserMenuData() {
        val callGetUser = api.retrieveExistingUser(getFirebaseTokenId())
        callGetUser.enqueue(object: Callback<List<UserItem>> {
            override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>>) {
                if(response.isSuccessful) {
                    user = response.body()!![0]
                    findViewById<TextInputEditText>(R.id.userName).setText(user.userName)
                    findViewById<TextInputEditText>(R.id.userHeadline).setText(user.userHeadline)
                }
            }
            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupEnterKeyListeners(){
        val userNameField =  findViewById<TextInputEditText>(R.id.userName)
        val userHeadlineField = findViewById<TextInputEditText>(R.id.userHeadline)
        userNameField.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                userNameField.isEnabled = false
                userHeadlineField.isEnabled = false
                userNameField.setText(userNameField.text.toString().trim())
                user.userName = userNameField.text.toString()
                api.updateExistingUser(getFirebaseTokenId(), user.userId.toString(), user ).enqueue(object:Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(response.isSuccessful)
                        {
                            getUserMenuData()
                            userNameField.isEnabled = true
                            userHeadlineField.isEnabled = true
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) { TODO("Not yet implemented") }
                })
                return@OnKeyListener true
            }
            false
        })
        userHeadlineField.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                userNameField.isEnabled = false
                userHeadlineField.isEnabled = false
                userHeadlineField.setText(userHeadlineField.text.toString().trim())
                user.userHeadline = userHeadlineField.text.toString()

                val updatedUser = UserItem(
                    getFirebaseTokenId(),
                    user.userHeadline,
                    user.userId,
                    user.userName
                )
                api.updateExistingUser(getFirebaseTokenId(), user.userId.toString(), updatedUser ).enqueue(object:Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(response.isSuccessful){
                            getUserMenuData()
                            userNameField.isEnabled = true
                            userHeadlineField.isEnabled = true
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) { TODO("Not yet implemented") }
                })
                return@OnKeyListener true
            }
            false
        })
    }
}

