package net.azurewebsites.athleet

import net.azurewebsites.athleet.ApiLib.Api
import org.junit.Test
import java.nio.charset.Charset
import org.junit.Assert.*

class UserProfilePageTests {

    @Test
    fun checkExistingUser() {
        val api = apiFactory()

        // response as raw string
        val rawResponse = api.checkExistingUser(tokenFactory()).execute().body()?.source()?.readString(charset = Charset.defaultCharset())
        val userData = rawResponse?.split("[", "]", "{", "}", ",",":")?.map { it.trim()}

        // response in form of User object
        val response = api.retrieveExistingUser(tokenFactory()).execute().body()?.get(0)
        val userName = response?.userName
        val userHeadline = response?.userHeadline

        assertEquals("\""+userName+"\"", userData?.get(5))
        assertEquals("\""+userHeadline+"\"", userData?.get(9))
    }
}