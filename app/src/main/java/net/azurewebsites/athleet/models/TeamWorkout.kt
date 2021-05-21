package net.azurewebsites.athleet.models

data class TeamWorkout(
    var WorkoutName: String,
    var Description: String,
    var TeamName: String
)

data class DeleteTeamWorkout(
    var WorkoutName: String,
    var TeamName: String
)