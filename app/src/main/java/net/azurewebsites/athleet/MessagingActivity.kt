package net.azurewebsites.athleet

import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)
        try {
            mSocket = IO.socket("http://10.0.2.2:3000")

            mSocket.on(Socket.EVENT_CONNECT, onConnectEvent)
            mSocket.on(Socket.EVENT_DISCONNECT, onDisconnectEvent)
            mSocket.on("new message", onNewMessageEvent)
            mSocket.on("user joined", onUserJoinedEvent)

            mSocket.connect()
        }
        catch (e: URISyntaxException)
        {
        // do nothing
        }

        userName = intent.getStringExtra("userName").toString()
        teamName = intent.getStringExtra(TEAM_NAME).toString()

        // send the message when the button is clicked
        send_message_button.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        // nice gift from Simeon and Ryan
        val text = findViewById<EditText>(R.id.editText).text?.toString().toString()
        val message = Message(userName, text, teamName, MessageType.CHAT_MINE.index)
        //addItemToRecyclerView(message)
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, "Cannot send text message that is empty!", Toast.LENGTH_LONG).show()
            return;
        }

        val gson = Gson()
        mSocket.emit("new message", gson.toJson(message))
    }

    private var onConnectEvent = Emitter.Listener {
        mSocket.emit("add user", FirebaseAuth.getInstance().currentUser!!.displayName!!)
    }

    private var onDisconnectEvent = Emitter.Listener {

    }

    private var onNewMessageEvent = Emitter.Listener {

    }

    private var onUserJoinedEvent = Emitter.Listener {

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