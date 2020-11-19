package net.azurewebsites.athleet

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class MainActivity : AppCompatActivity() {
    private var coroutineJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button = findViewById<Button>(R.id.button)
        var apiText = findViewById<TextView>(R.id.textView)
        var userData = ""
        val apiProvider = Api.createSafe("https://jpathleetapi.azurewebsites.net/api/")

        button.setOnClickListener {
            //if out side of button it will crash because it was already executed
            val call = apiProvider.getAllUsers()
            call.enqueue(object : Callback<List<UserItem>> {
                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                    apiText.text = "failure"
                }

                override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>>) {
                    apiText.text = response.body()?.get(0)?.userName.toString()
                }
            })
        }
    }
}

