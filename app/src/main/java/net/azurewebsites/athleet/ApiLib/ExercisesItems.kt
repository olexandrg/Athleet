package net.azurewebsites.athleet.ApiLib

data class ExercisesItem(
    val defaultReps: String?,
    val description: String?,
    val exerciseId: Int?,
    val exerciseName: String?
)

interface ExercisesHandler {
    companion object {
        // returns exercise ID
        fun returnExerciseID(list: List<ExercisesItem>?, exerciseName: String?): Int? {
            return try {
                list?.filter {it.exerciseName == exerciseName}?.get(0)?.exerciseId

            } catch(e: IndexOutOfBoundsException) {
                println(e.message)
                -999
            }
        }
    }
}
