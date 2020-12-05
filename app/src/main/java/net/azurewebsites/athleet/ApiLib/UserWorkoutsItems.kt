package net.azurewebsites.athleet.ApiLib

data class UserWorkoutsItem(
    val userId: Int?,
    val userWorkoutId: Int?,
    val workoutDate: String?,
    val workoutId: Int?
)

interface UserWorkoutsItemsHandler {
    companion object {
        // returns user workout ID
        fun returnUserWorkoutID(workoutsList: List<WorkoutItem>?, userWorkoutsList: List<UserWorkoutsItem>?, workoutName: String): Int? {
            return try {
                val workoutId = workoutHandler.returnWorkoutID(workoutsList, workoutName)
                return userWorkoutsList?.filter {it.workoutId == workoutId}?.get(0)?.userWorkoutId
            } catch(e: IndexOutOfBoundsException) {
                0
            }
        }
    }
}
