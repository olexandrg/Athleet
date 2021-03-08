package net.azurewebsites.athleet

import net.azurewebsites.athleet.ApiLib.Api
import org.junit.Test
import java.nio.charset.Charset

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

        // Complete JSON response
        println(response)

        // User name
        println(userData?.get(5))

        // Headline
        println(userData?.get(9))
    }
}