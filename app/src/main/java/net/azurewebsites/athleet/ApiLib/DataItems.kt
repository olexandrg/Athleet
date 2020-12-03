package net.azurewebsites.athleet

import java.lang.Exception
import kotlin.IndexOutOfBoundsException

//      Child items of Data
data class UserItem(
    val firebaseUID: String,
    val userHeadline: String?,
    val userId: Int,
    val userName: String
)



interface userHander {
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

        // adds new user
        fun returnUserID(list: List<UserItem>?, userName: String): String {
            return try {
                list?.filter {it.userName == userName}?.get(0)?.userId.toString()

            } catch(e: IndexOutOfBoundsException) {
                "User $userName not found."
            }
        }
    }
}