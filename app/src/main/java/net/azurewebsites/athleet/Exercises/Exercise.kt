package net.azurewebsites.athleet.Exercises
import androidx.annotation.DrawableRes
import java.util.*

data class Exercise(
    var name: String?,
    val description: String?,
    val reps: Int?,
    val sets:Int?,
    val unitType:String?,
    val unitCount:Float?
)