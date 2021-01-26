package net.azurewebsites.athleet.Teams
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Workouts.Workout
import java.time.Instant
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun TeamsList(resources: Resources): List<TeamItem> {
    return listOf(TeamItem(1, teamName = "Team One", teamDescription = "The first team"), TeamItem(2,"Team Two",teamDescription = "The second team"))
}