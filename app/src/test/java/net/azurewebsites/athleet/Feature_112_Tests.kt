package net.azurewebsites.athleet

import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*

class Feature_112_Tests {
    private fun apiFactory(): Api {return Api.createSafe()}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    @Test
    fun getTeamMessagesTest() {
        val api = apiFactory()

        val teamMessages = api.getUserConvList(tokenFactory()).execute()

        assertEquals("GET /api/Chat/user/list", 200, teamMessages.code())
    }

    @Test
    fun getUserMessages() {
        val api = apiFactory()

        val teamMessages = api.getUserMessages(tokenFactory(), 47).execute()

        assertEquals("GET /api/Chat/user", 200, teamMessages.code())
    }

    @Test
    fun CreateUserConvTest() {
        val api = apiFactory()

        val teamMessages = api.CreateUserConv(tokenFactory(), "alexoit32").execute()

        assertEquals("POST /api/Chat/user", 200, teamMessages.code())
    }
}