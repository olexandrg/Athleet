package net.azurewebsites.athleet

import kotlin.IndexOutOfBoundsException

data class WorkoutItem (
        val workoutId: String?,
        val workoutName: String,
        val description: String
)

interface workoutHandler {
    companion object {
        @Throws (IndexOutOfBoundsException::class)
        // returns username, if found
        fun returnWorkoutName(list: List<WorkoutItem>?, workoutName: String): String {
            return try {
                val response = list?.filter {it.workoutName == workoutName}?.get(0)?.workoutName?.toString()
                if (response == "kotlin.Unit") throw IndexOutOfBoundsException()
                response.toString()
            } catch(e: IndexOutOfBoundsException) {
                "Workout $workoutName not found."
            }
        }

        // returns workout ID
        fun returnWorkoutID(list: List<WorkoutItem>?, workoutName: String): String {
            return try {
                list?.filter {it.workoutName == workoutName}?.get(0)?.workoutId.toString()

            } catch(e: IndexOutOfBoundsException) {
                "Workout $workoutName not found."
            }
        }
    }
}