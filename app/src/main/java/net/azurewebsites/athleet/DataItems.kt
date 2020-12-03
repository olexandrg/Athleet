package net.azurewebsites.athleet

import java.lang.Exception
import kotlin.IndexOutOfBoundsException

//      Child items of Data
data class UserItem(
    val firebaseUID: String,
    val userHeadline: String,
    val userId: Int,
    val userName: String
)

interface userHander {
    companion object {
        @Throws (IndexOutOfBoundsException::class)
        fun userName(list: List<UserItem>?, userName: String): String {
            try {
                val response = list?.filter {it.userName == userName}?.get(0)?.userName.toString() ?: throw java.lang.IndexOutOfBoundsException()
                if (response == "kotlin.Unit") throw IndexOutOfBoundsException()
                return response
            }
            catch(e: IndexOutOfBoundsException) {
                return "User $userName not found."
            }
        }
    }
}