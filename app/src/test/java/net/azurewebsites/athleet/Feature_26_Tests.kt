package net.azurewebsites.athleet

import junit.framework.Assert
import org.junit.Test

// feature 26
// Invite players into team
class Feature_26_Tests {

    @Test // will test to see if user within team is updated to coach
    fun inviteUserIntoTeam() {
        val api = apiFactory()
        val responseCode = api.inviteExistingUserToTeam(tokenFactory(), "New Test Team Name 2", "515PM Test").execute().code()
        Assert.assertEquals(200, responseCode)
    }
}