package net.azurewebsites.athleet.ApiLib

data class TeamInfo(
    var TeamName: String,
    var users: List<TeamUser>,
    var workoutNames: Array<String>
)