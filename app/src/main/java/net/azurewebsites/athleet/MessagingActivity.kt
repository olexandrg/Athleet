package net.azurewebsites.athleet

import java.text.SimpleDateFormat
import java.util.*
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import io.socket.client.Socket
import io.socket.client.IO;
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_chatroom.*
import kotlinx.android.synthetic.main.activity_messaging.*
import kotlinx.android.synthetic.main.activity_messaging.send_message_button
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.chat.ChatRoomAdapter
import net.azurewebsites.athleet.chat.Message
import net.azurewebsites.athleet.chat.MessageType
import net.azurewebsites.athleet.models.Conversation
import retrofit2.Callback
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.net.URISyntaxException

class MessagingActivity : AppCompatActivity() {
    private lateinit var mSocket: Socket
    private lateinit var message: String
    private lateinit var userName: String
    private lateinit var teamName: String
    private var convID: Int = 0

    val chatList: ArrayList<Message> = arrayListOf()
    lateinit var chatRoomAdapter: ChatRoomAdapter

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)
        try {
            mSocket = IO.socket("https://athleet-chat.herokuapp.com")
            //mSocket = IO.socket("http://10.0.2.2:3000")

            mSocket.on(Socket.EVENT_CONNECT, onConnectEvent)
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onError)
            mSocket.on("new message", onNewMessageEvent)
            mSocket.on("user joined", onUserJoinedEvent)
            mSocket.on("user left", onUserLeftEvent)
            mSocket.on("updateChat", onUpdateChat)

            mSocket.connect()
        }
        catch (e: URISyntaxException)
        {
        // do nothing
        }

        userName = intent.getStringExtra("userName").toString()
        teamName = intent.getStringExtra(TEAM_NAME).toString()
        editText = findViewById(R.id.editText)

        //Set Chatroom adapter

        chatRoomAdapter = ChatRoomAdapter(this, chatList);
        recyclerView.adapter = chatRoomAdapter;

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // send the message when the button is clicked
        send_message_button.setOnClickListener {
            sendMessage()
        }

        val apiCall = Api.createSafe().getTeamConversation(getFirebaseTokenId(), teamName)
        apiCall.enqueue(object: Callback<List<Conversation>> {
            override fun onResponse(call: Call<List<Conversation>>, response: Response<List<Conversation>>) {
                val messages: List<Conversation> = response.body()!!
                if (messages.count() == 0)
                    return
                for (message in messages)
                {
                    var type: Int
                    if (message.userName == userName)
                        type = MessageType.CHAT_MINE.index
                    else
                        type = MessageType.CHAT_PARTNER.index
                    val newMessage = Message(message.userName, message.messageContent, teamName, message.messageDate, type)
                    addItemToRecyclerView(newMessage)
                }
                convID = messages[0].conversationID
            }

            override fun onFailure(call: Call<List<Conversation>>, t: Throwable) { Toast.makeText(applicationContext, "Failed to load messages", Toast.LENGTH_LONG).show() }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
    }

    private fun sendMessage() {
        // nice gift from Simeon and Ryan
        val text = findViewById<EditText>(R.id.editText).text?.toString().toString()

        // get the current time
        val currentTime = SimpleDateFormat("HH:mm")
        val currentTimeString: String = currentTime.format(Date())

        val message = Message(userName, text, teamName, currentTimeString, MessageType.CHAT_MINE.index)
        addItemToRecyclerView(message)
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, "Cannot send text message that is empty!", Toast.LENGTH_LONG).show()
            return;
        }

        val gson = Gson()
        mSocket.emit("new message", gson.toJson(message))
        val apiCall = Api.createSafe().saveMessage(getFirebaseTokenId(), convID, text)
        apiCall.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) { }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) { Toast.makeText(applicationContext, "Failed to save message", Toast.LENGTH_LONG).show() }
        })
    }

    private var onConnectEvent = Emitter.Listener { mSocket.emit("add user", FirebaseAuth.getInstance().currentUser!!.displayName!!, teamName) }
    private var onNewMessageEvent = Emitter.Listener {}
    private var onUserJoinedEvent = Emitter.Listener {
        val gson = Gson()
        val chat: Message = gson.fromJson(it[0].toString(), Message::class.java)
        chat.viewType = MessageType.USER_JOIN.index
        addItemToRecyclerView(chat)
    }

    private var onUserLeftEvent = Emitter.Listener {
        val gson = Gson()
        val chat: Message = gson.fromJson(it[0].toString(), Message::class.java)
        chat.viewType = MessageType.USER_LEAVE.index
        addItemToRecyclerView(chat)
    }

    private var onError = Emitter.Listener {
        Log.e("Socket.IO", "Connect Error")
    }

    var onUpdateChat = Emitter.Listener {
        val gson = Gson()
        val chat: Message = gson.fromJson(it[0].toString(), Message::class.java)
        chat.viewType = MessageType.CHAT_PARTNER.index
        addItemToRecyclerView(chat)
    }

    private fun addItemToRecyclerView(message: Message) {
        //Since this function is inside of the listener,
        // You need to do it on UIThread!
        runOnUiThread {
            chatList.add(message)
            chatRoomAdapter.notifyItemInserted(chatList.size)
            editText.setText("")
            recyclerView.scrollToPosition(chatList.size - 1) //move focus on last message
        }
    }
}