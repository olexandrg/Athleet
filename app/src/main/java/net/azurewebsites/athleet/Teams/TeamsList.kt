package net.azurewebsites.athleet.Teams
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun TeamsList(resources: Resources): List<Team> {
    return listOf(Team(1, teamName = "Team One", teamDescription = "The first team"), Team(2,"Team Two",teamDescription = "The second team"))
}