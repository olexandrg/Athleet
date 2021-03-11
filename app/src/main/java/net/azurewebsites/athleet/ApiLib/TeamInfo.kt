package net.azurewebsites.athleet.ApiLib

data class TeamInfo(
    var teamName: String,
    var users: List<TeamUser>,
    var workoutNames: Array<String>
)