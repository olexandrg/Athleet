package net.azurewebsites.athleet.models

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.azurewebsites.athleet.Teams.TeamMembersList
import net.azurewebsites.athleet.Teams.TeamsList
import net.azurewebsites.athleet.exercise.Exercise
import net.azurewebsites.athleet.workouts.WorkoutList
@RequiresApi(Build.VERSION_CODES.O)
// Handles operations on Live Data and holds details about it.
class DataSource(resources: Resources) {
     var blockedUsersList: MutableList<String> = mutableListOf(String())
     var BlockedUsersLiveData = MutableLiveData(blockedUsersList)

    private var initialTeamMembersList = TeamMembersList(resources)

    private var initialWorkoutList = WorkoutList(resources)
    private val WorkoutsLiveData = MutableLiveData(initialWorkoutList)
    private val TeamWorkoutsLiveData = MutableLiveData(initialWorkoutList)

    private val initialTeamsList = TeamsList(resources)
    private val TeamsLiveData = MutableLiveData(initialTeamsList)
    var currentWorkout: Workout? = null
    private var ExercisesLiveData:MutableLiveData<List<Exercise>> = MutableLiveData(listOf(Exercise(null,null,null,null,null,null)))

    fun addWorkouts(list: List<Workout>) { WorkoutsLiveData.postValue(list) }
    fun addTeamWorkouts(list: List<Workout>) { TeamWorkoutsLiveData.postValue(list) }

    fun getWorkoutList(): LiveData<List<Workout>> { return WorkoutsLiveData }
    fun getTeamWorkoutList(): LiveData<List<Workout>> { return TeamWorkoutsLiveData }


    fun getTeamsList(): LiveData<List<Team>> { return TeamsLiveData }
    fun addTeams(list: List<Team>) { TeamsLiveData.postValue(list) }

    fun setBlockList(usernames: MutableList<String>){blockedUsersList = usernames; BlockedUsersLiveData.postValue(blockedUsersList)}

    fun addTeam(newTeam: Team) {
        val currentList = TeamsLiveData.value
        if (currentList == null) { TeamsLiveData.postValue(listOf(newTeam)) }
        else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, newTeam)
            TeamsLiveData.postValue(updatedList)
        }
    }

    fun getExerciseList(): LiveData<List<Exercise>> { return ExercisesLiveData }
    fun addExercise(newExercise: Exercise) {
        if(currentWorkout != null)
        {
            val currentList = ExercisesLiveData.value
            val updatedList=currentList!!.toMutableList()
                updatedList.add(0,newExercise)
                ExercisesLiveData.postValue(updatedList)
        }
    }

    private val TeamMembersLiveData = MutableLiveData(initialTeamMembersList)
    fun setTeamList(list: List<TeamUser>) { TeamMembersLiveData.postValue(list) }
    fun getTeamMembersList(): LiveData<List<TeamUser>> { return TeamMembersLiveData }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE
                    ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}