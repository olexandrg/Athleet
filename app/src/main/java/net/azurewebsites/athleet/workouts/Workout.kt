package net.azurewebsites.athleet.workouts
import net.azurewebsites.athleet.Exercises.Exercise

data class Workout(
    var name: String,
    //val lastCompleted: Date,
    val description: String,
    var exercises:List<Exercise>?
)