package net.azurewebsites.athleet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.socket.client.Socket
import io.socket.client.IO;
import java.net.URISyntaxException

class MessagingActivity : AppCompatActivity() {
    private var mSocket: Socket? = null
    private var message: String

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
    }
}