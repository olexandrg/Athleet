package net.azurewebsites.athleet.ApiLib

import kotlin.IndexOutOfBoundsException

//      Child items of Data
data class UserItem(
    val firebaseUID: String?,
    val userHeadline: String?,
    val userId: Int?,
    val userName: String
)

interface userHandler {
    companion object {
        @Throws (IndexOutOfBoundsException::class)
        // returns username, if found
        fun returnUserName(list: List<UserItem>?, userName: String): String {
            return try {
                val response = list?.filter {it.userName == userName}?.get(0)?.userName.toString()
                if (response == "kotlin.Unit") throw IndexOutOfBoundsException()
                response
            } catch(e: IndexOutOfBoundsException) {
                "User $userName not found."
            }
        }

        // returns user ID
        fun returnUserID(list: List<UserItem>?, userName: String): Int? {
            return try {
                list?.filter {it.userName == userName}?.get(0)?.userId

            } catch(e: IndexOutOfBoundsException) {
                0
            }
        }
    }
}