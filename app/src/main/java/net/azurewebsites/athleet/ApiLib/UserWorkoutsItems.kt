package net.azurewebsites.athleet.ApiLib

import java.text.SimpleDateFormat

//data class UserWorkoutsItem(
//        val userWorkoutId: String?,
//        val userId: String,
//        val workoutId: String
//        //val workoutDate: SimpleDateFormat?
//)

//data class UserWorkoutsItem : ArrayList<UserWorkoutsItemItem>()

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
