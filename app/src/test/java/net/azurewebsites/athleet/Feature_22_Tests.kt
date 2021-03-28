package net.azurewebsites.athleet

import junit.framework.Assert.assertEquals
import net.azurewebsites.athleet.ApiLib.Api
import org.junit.Test

// feature 22
// As an admin, have the ability to update someone to an admin
class feature_22_Tests {

    @Test // will test to see if user within team is updated to coach
    fun updateTeamUserToCoach() {
        val api = apiFactory()
        val responseCode = api.makeTeamUserCoach(tokenFactory(), "New Test Team Name 2", "Testing Fixed Increment 1", true).execute().code()
        assertEquals(200, responseCode)
    }
}