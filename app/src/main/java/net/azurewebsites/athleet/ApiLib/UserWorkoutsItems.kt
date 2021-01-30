package net.azurewebsites.athleet.ApiLib

data class UserWorkouts(
    val userId: Int?,
    val userWorkoutId: Int?,
    val workoutDate: String?,
    val workoutId: Int?
)

interface UserWorkoutsItemsHandler {
    companion object {
        // returns user workout ID
        fun returnUserWorkoutID(workoutsList: List<WorkoutItem>?, userWorkoutsList: List<UserWorkouts>?, workoutName: String): Int? {
            return try {
                val workoutId = workoutHandler.returnWorkoutID(workoutsList, workoutName)
                return userWorkoutsList?.filter {it.workoutId == workoutId}?.get(0)?.userWorkoutId
            } catch(e: IndexOutOfBoundsException) {
                0
            }
        }
    }
}
