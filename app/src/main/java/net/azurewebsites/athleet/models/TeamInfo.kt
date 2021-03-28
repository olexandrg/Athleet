package net.azurewebsites.athleet.models

data class TeamInfo(
    var teamName: String,
    var users: List<TeamUser>,
    var workoutNames: Array<String>
)