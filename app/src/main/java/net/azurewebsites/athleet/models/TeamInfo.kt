package net.azurewebsites.athleet.models

import net.azurewebsites.athleet.ApiLib.TeamUser

data class TeamInfo(
    var teamName: String,
    var users: List<TeamUser>,
    var workoutNames: Array<String>
)