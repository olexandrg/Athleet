package net.azurewebsites.athleet.Workouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import net.azurewebsites.athleet.R

class WorkoutDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_detail)
        findViewById<Button>(R.id.btn_complete_new).setOnClickListener { setResult(1) ; finish(); }
        findViewById<Button>(R.id.btn_cancel).setOnClickListener { setResult(0) ; finish(); }
    }
}