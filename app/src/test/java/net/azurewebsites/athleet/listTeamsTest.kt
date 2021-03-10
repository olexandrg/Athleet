package net.azurewebsites.athleet

import junit.framework.Assert.assertEquals
import net.azurewebsites.athleet.ApiLib.Api
import org.junit.Test

class listTeamsTest {
     private fun apiFactory(): Api {return Api.createSafe()}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    @Test
    fun listTeamSuccess() {
        val api = apiFactory()

        val teams = api.listTeams(tokenFactory()).execute()

        assertEquals("GET api/team/list", 200, teams.code())
    }
}