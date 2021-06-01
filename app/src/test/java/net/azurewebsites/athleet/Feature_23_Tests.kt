package net.azurewebsites.athleet

import android.util.Log
import kotlinx.coroutines.runBlocking
import net.azurewebsites.athleet.models.DeleteTeamWorkout
import net.azurewebsites.athleet.models.Exercise
import net.azurewebsites.athleet.models.TeamWorkout
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class Feature_23_Tests{

    private val api = apiFactory()
    private lateinit var teamName: String
    private lateinit var workoutDescription: String
    private lateinit var workoutName: String

    @Before fun initialize() {
        teamName = "TestingName"
        workoutName = "TestFeature23TeamWorkout"
        workoutDescription = "test team workout"
    }

    // TESTS FOR -> ApiProvider.updateExercise(token, exerciseId, Exercise)
    @Test
    fun `createTeamWorkout should return 201 and deleteTeamWorkout should return 204` () {

//        // Create Team Workout
//        var expected = 201
//        var response = api.createTeamWorkout(tokenFactory(), TeamWorkout(workoutName, workoutDescription, teamName)).execute()
//        assertEquals("response code is 201", expected, response.code())
//
//        // Delete Team Workout
//        expected = 204
//        response = api.deleteTeamWorkout(tokenFactory(), DeleteTeamWorkout(workoutName,teamName)).execute()
//        assertEquals("response code is 204", expected, response.code())
    }

    @Test
    fun `getTeamID returns id for Team` () {
        val expected = 55
        val result = runBlocking {  api.getTeamId(tokenFactory(), "Aardvark")}
        assertEquals("teamID is 55", expected, result)
    }

    @Test
    fun `First element's Name in getTeamWorkouts should match GetList` () {
        val expected = "GetList"
        val result = runBlocking { api.getTeamWorkouts(tokenFactory(), 65)[0] }.workoutName
        assertEquals("workoutName is GetList", expected, result)
    }



}