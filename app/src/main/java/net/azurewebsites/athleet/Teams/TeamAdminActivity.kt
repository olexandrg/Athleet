package net.azurewebsites.athleet.Teams

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_team_admin.*
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.Dashboard.TEAM_NAME
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.getFirebaseTokenId
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val USER_PROMOTED = 2
const val NO_CHANGES_BY_ADMIN = 0
class TeamAdminActivity : AppCompatActivity() {
    val api = Api.createSafe()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_team_admin)
        val userInput = findViewById<EditText>(R.id.editText_makeUserAdmin)
        val makeAdminButton = findViewById<Button>(button_makeUserAdmin.id)
        makeAdminButton.setOnClickListener {
            api.makeTeamUserCoach(getFirebaseTokenId(), intent?.getStringExtra(TEAM_NAME)!!,userInput.text.toString(), true).enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.i("TeamAdminActivity:", "Successfully promoted user '" + userInput.text.toString() + "' to Coach.");
                    setResult(USER_PROMOTED)
                    finishActivity(UPDATED_ADMINS_STATUS) }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("TeamAdminActivity ERROR", "Failed to promote user '" + userInput.text.toString() + "' to Coach. Cause:", t.cause);
                    setResult(NO_CHANGES_BY_ADMIN) }
            })
        }

    }

}