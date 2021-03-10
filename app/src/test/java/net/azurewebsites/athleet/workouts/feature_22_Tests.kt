import junit.framework.Assert.assertEquals
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.tokenMaster
import org.junit.Test

class feature_22_Tests {
    private fun apiFactory(): Api {return Api.createSafe()}

    private fun tokenFactory(): String {
        return tokenMaster.tokenFactory()
    }

    @Test // will test to see if user within team is updated to coach
    fun updateTeamUserToCoach() {
        val api = apiFactory()
        val responseCode = api.makeTeamUserCoach(tokenFactory(), "New Test Team Name 2", "Testing Fixed Increment 1", true).execute().code()
        assertEquals(200, responseCode)
    }
}