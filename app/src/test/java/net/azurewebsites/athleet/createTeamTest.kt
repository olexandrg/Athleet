package net.azurewebsites.athleet

import org.junit.Assert.assertEquals
import org.junit.Test

class createTeamTests {

    @Test
    fun deleteSuccess() {
        val api = apiFactory()

        val createdTeam = api.createTeam(tokenFactory(), "Test Team", "This is a test team").execute()

        assertEquals("GET /api/Team/leave", 200, createdTeam.code())
    }
}