package net.azurewebsites.athleet.Workouts
import androidx.annotation.DrawableRes
import java.util.*

data class Workout(
    var name: String,
    val lastCompleted: Date,
    val description: String
)