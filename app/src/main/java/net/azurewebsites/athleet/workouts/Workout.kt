package net.azurewebsites.athleet.workouts
import net.azurewebsites.athleet.exercise.Exercise

data class Workout(
    var name: String,
    //val lastCompleted: Date,
    val description: String,
    var exercises:List<Exercise>?
)