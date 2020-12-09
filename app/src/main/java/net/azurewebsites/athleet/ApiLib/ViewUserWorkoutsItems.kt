package net.azurewebsites.athleet.ApiLib

data class ViewUserWorkoutsItem(
    val description: String,
    val userId: Int,
    val userName: String,
    val workoutDate: String,
    val workoutId: Int,
    val workoutName: String
)