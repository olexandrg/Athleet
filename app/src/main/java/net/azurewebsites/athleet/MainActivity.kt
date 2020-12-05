package net.azurewebsites.athleet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import net.azurewebsites.athleet.UserAuth.WorkoutsListViewModel
//import com.example.recyclersample.flowerDetail.FlowerDetailActivity

class MainActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreferences"
    private val workoutsListViewModel by viewModels<WorkoutsListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

    }


}