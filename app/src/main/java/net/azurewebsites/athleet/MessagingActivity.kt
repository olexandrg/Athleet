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
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.chat.ChatRoomAdapter
import net.azurewebsites.athleet.chat.Message
import net.azurewebsites.athleet.chat.MessageType
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

class MessagingActivity : AppCompatActivity() {
    private lateinit var mSocket: Socket
    private lateinit var message: String
    private lateinit var userName: String
    private lateinit var teamName: String

    val chatList: ArrayList<Message> = arrayListOf()
    lateinit var chatRoomAdapter: ChatRoomAdapter

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)
        try {
            // mSocket = IO.socket("https://thing34343.herokuapp.com")
            mSocket = IO.socket("http://10.0.2.2:3000")

            mSocket.on(Socket.EVENT_CONNECT, onConnectEvent)
            mSocket.on(Socket.EVENT_DISCONNECT, onDisconnectEvent)
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
    }

    private var onConnectEvent = Emitter.Listener {
        mSocket.emit("add user", FirebaseAuth.getInstance().currentUser!!.displayName!!, teamName)
    }

    private var onDisconnectEvent = Emitter.Listener {
        mSocket.emit("disconnect", FirebaseAuth.getInstance().currentUser!!.displayName!!, teamName)
    }

    private var onNewMessageEvent = Emitter.Listener {

    }

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

    /*private fun thing() {
        val content = editText.text.toString()
        val sendData = SendMessage(userName, content, roomName)
        val jsonData = gson.toJson(sendData)
        mSocket.emit("newMessage", jsonData)

        val message = Message(userName, content, roomName, MessageType.CHAT_MINE.index)
        addItemToRecyclerView(message)
    }*/
}