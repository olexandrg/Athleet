package net.azurewebsites.athleet.user

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.blocked_user_list_item.*
import kotlinx.android.synthetic.main.blocked_user_list_item.view.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.DataSource
import net.azurewebsites.athleet.models.Team
import net.azurewebsites.athleet.models.TeamUser
import net.azurewebsites.athleet.models.UserItem
import okhttp3.ResponseBody
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserProfilePageActivity : AppCompatActivity() {
    // store user data locally
    val api = Api.createSafe()
    private lateinit var blockedUserListAdapter:BlockedUserListAdapter
    private val blockedUsersListViewModel by viewModels<BlockedUsersListViewModel>{BlockedUsersListViewModelFactory(this)}
    private lateinit var rv:RecyclerView
    private lateinit var user: UserItem
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataSource:DataSource
    private var userEmail = FirebaseAuth.getInstance().currentUser?.email
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        // populate menu with retrieved user data
        getUserMenuData()
        setupEnterKeyListeners()
        dataSource = DataSource.getDataSource(resources)

        blockedUserListAdapter = BlockedUserListAdapter { user->dataSource.unblockUser(user) }
        blockedUsersListViewModel.blockedUsersLiveData.observe(this) {
            it.let {
                if(blockedUsersListViewModel.blockedUsersLiveData.value!!.size != 0)
                    blockedUserListAdapter.submitList(it as MutableList<String>);
                blockedUserListAdapter.notifyDataSetChanged()
            }
        }
        val emailField = findViewById<TextView>(R.id.userEmail)
        emailField.setText(userEmail)

        rv = findViewById(R.id.recyclerView_blockedUsers)
        rv.adapter = blockedUserListAdapter
        linearLayoutManager = LinearLayoutManager(this)
        rv.layoutManager = linearLayoutManager

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }
    class BlockedUsersListViewModel(val dataSource: DataSource) : ViewModel() {

        @RequiresApi(Build.VERSION_CODES.O)
        public val blockedUsersLiveData = dataSource.BlockedUsersLiveData
        @RequiresApi(Build.VERSION_CODES.O)
        fun updateBlockedUserList(list:List<String>) {
            dataSource.blockedUsersList = list as MutableList<String>
            dataSource.BlockedUsersLiveData.postValue(dataSource.blockedUsersList)
        }
    }

    class BlockedUsersListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BlockedUsersListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BlockedUsersListViewModel(
                    dataSource = DataSource.getDataSource(context.resources)
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
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

