package net.azurewebsites.athleet

import junit.framework.Assert.assertEquals
import org.junit.Test

class listTeamsTest {

    @Test
    fun listTeamSuccess() {
        val api = apiFactory()

        val teams = api.listTeams(tokenFactory()).execute()

        assertEquals("GET api/team/list", 200, teams.code())
    }
}