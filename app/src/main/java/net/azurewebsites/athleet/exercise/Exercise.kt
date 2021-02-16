package net.azurewebsites.athleet.exercise

data class Exercise(
    var name: String?,
    val description: String?,
    val reps: Int?,
    val sets:Int?,
    val unitType:String?,
    val unitCount:Float?
)