package net.azurewebsites.athleet

import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*

class Feature_113_Tests {
    private fun apiFactory(): Api {return Api.createSafe()}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    @Test
    fun saveTeamMessageTest() {
        val api = apiFactory()

        val saveMessage = api.saveMessage(tokenFactory(), 21, "Unit test, test message").execute()

        assertEquals("POST /api/Chat/team", 201, saveMessage.code())
    }

    @Test
    fun getTeamMessagesTest() {
        val api = apiFactory()
        val teamName = "Supert Team"

        val teamMessages = api.getTeamConversation(tokenFactory(), teamName).execute()

        assertEquals("GET /api/Team/", 200, teamMessages.code())
    }
}
