package net.azurewebsites.athleet

import android.util.Log
import kotlinx.coroutines.runBlocking
import net.azurewebsites.athleet.models.Exercise
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class Feature_34_Tests {

    private val api = apiFactory()

    @Test
    fun getWarningShouldRetrieve200ForNonWarnedUser () {
        val responseCode = api.getWarnings(tokenFactory()).execute().code()
        assertEquals(200, responseCode)
    }
}