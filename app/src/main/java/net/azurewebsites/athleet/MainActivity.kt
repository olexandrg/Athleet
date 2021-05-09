package net.azurewebsites.athleet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.azurewebsites.athleet.exercise.Exercise
import net.azurewebsites.athleet.models.DataSource

lateinit var dataSource:DataSource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataSource = DataSource.getDataSource(resources)
        setContentView(R.layout.activity_main)
    }
}