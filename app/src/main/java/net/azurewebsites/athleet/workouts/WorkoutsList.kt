package net.azurewebsites.athleet.workouts

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import net.azurewebsites.athleet.models.Workout

/* Returns initial list of Workouts. */
@RequiresApi(Build.VERSION_CODES.O)
fun WorkoutList(resources: Resources): List<Workout> {
    return mutableListOf(
    )
}