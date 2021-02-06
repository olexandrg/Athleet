package net.azurewebsites.athleet.Workouts
import androidx.annotation.DrawableRes
import net.azurewebsites.athleet.Exercises.Exercise
import java.util.*

data class Workout(
    var name: String,
    //val lastCompleted: Date,
    val description: String,
    var exercises:List<Exercise>?
)