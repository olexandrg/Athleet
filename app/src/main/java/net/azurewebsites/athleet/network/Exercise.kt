package net.azurewebsites.athleet.network

data class Exercise (
    val exerciseId: Int,
    val exerciseName: String,
    val description: String,
    val defaultReps: String
)
