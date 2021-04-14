package net.azurewebsites.athleet

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import io.socket.client.Socket
import io.socket.client.IO;
import kotlinx.android.synthetic.main.activity_messaging.*
import java.net.URISyntaxException

class MessagingActivity : AppCompatActivity() {
    private var mSocket: Socket? = null
    private lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        try {
            mSocket = IO.socket("10.0.2.2:3000")
            mSocket!!.connect()
        }
        catch (e: URISyntaxException)
        {
        //do nothing
        }

        // send the message when the button is clicked
        send_message_button.setOnClickListener {
            sendMessage()
        }
        /*
        private EditText mInputMessageView;

private void attemptSend() {
    String message = mInputMessageView.getText().toString().trim();
    if (TextUtils.isEmpty(message)) {
        return;
    }

    mInputMessageView.setText("");
    mSocket.emit("new message", message);
}
         */
    }

    private fun sendMessage() {
        // nice gift from Simeon and Ryan
        message = message_input.text?.toString().toString()
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Cannot send text message that is empty!", Toast.LENGTH_LONG).show()
            return;
        }

        mSocket!!.emit("new message", message)
    }
}