package net.azurewebsites.athleet.models
import net.azurewebsites.athleet.exercise.Exercise

data class Workout(
    var workoutName: String,
    val description: String,
    val workoutId:Int?,
    var exercises:List<Exercise>?
)