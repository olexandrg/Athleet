//package net.azurewebsites.athleet.ApiLib
//
//import kotlin.IndexOutOfBoundsException
//
//data class WorkoutItem (
//        val workoutId: Int?,
//        val workoutName: String,
//        val description: String
//)
//
//interface workoutHandler {
//    companion object {
//        // returns workout object
//        fun returnWorkout(list: Workouts, workoutName: String): WorkoutItem {
//            return list?.filter { it.workoutName == workoutName }?.get(0)
//        }
//        @Throws (IndexOutOfBoundsException::class)
//        // returns workout name, if found
//        fun returnWorkoutName(list: List<WorkoutItem>?, workoutName: String): String {
//            return try {
//                val response = list?.filter {it.workoutName == workoutName}?.get(0)?.workoutName?.toString()
//                if (response == "kotlin.Unit") throw IndexOutOfBoundsException()
//                response.toString()
//            } catch(e: IndexOutOfBoundsException) {
//                "Workout $workoutName not found."
//            }
//        }
//
//        // returns workout ID
//        fun returnWorkoutID(list: List<WorkoutItem>?, workoutName: String): Int? {
//            return try {
//                list?.filter {it.workoutName == workoutName}?.get(0)?.workoutId
//
//            } catch(e: IndexOutOfBoundsException) {
//                0
//            }
//        }
//    }
//}