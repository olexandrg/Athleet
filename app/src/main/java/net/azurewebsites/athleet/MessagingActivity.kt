package net.azurewebsites.athleet

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import io.socket.client.Socket
import io.socket.client.IO;
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_messaging.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

class MessagingActivity : AppCompatActivity() {
    private lateinit var mSocket: Socket
    private lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
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

        // send the message when the button is clicked
        send_message_button.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        // nice gift from Simeon and Ryan
        message = message_input.text?.toString().toString()
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Cannot send text message that is empty!", Toast.LENGTH_LONG).show()
            return;
        }

        mSocket.emit("new message", message)
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
}