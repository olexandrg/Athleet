package net.azurewebsites.athleet

import net.azurewebsites.athleet.ApiLib.*
import org.junit.Test
import org.junit.Assert.*

class leaveTeamTests {
    private fun apiFactory(): Api {return Api.createSafe()}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    @Test
    fun deleteSuccess() {
        val api = apiFactory()
        val teamName = "New Test Team Name 2"

        val leaveTeam = api.leaveTeam(tokenFactory(), teamName).execute()

        assertEquals("GET /api/Team/leave", 200, leaveTeam.code())
    }
}