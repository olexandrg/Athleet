package net.azurewebsites.athleet

import net.azurewebsites.athleet.ApiLib.Api
import org.junit.Test
import java.nio.charset.Charset
import org.junit.Assert.*
import kotlin.text.Typography.quote

class UserProfilePageTests {
    private fun apiFactory(): Api {return Api.createSafe()}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    @Test
    fun checkExistingUser() {
        val api = apiFactory()
        val response = api.checkExistingUser(tokenFactory()).execute().body()?.source()?.readString(charset = Charset.defaultCharset())
        val userData = response?.split("[", "]", "{", "}", ",",":")?.map { it.trim()}

        // User name
        assertEquals("\"TestUseraGKhtr\"", userData?.get(5))

        // Headline
        assertEquals("\"Test Add headline\"", userData?.get(9))
    }
}