package net.azurewebsites.athleet.models

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.azurewebsites.athleet.exercise.Exercise
import net.azurewebsites.athleet.Teams.TeamsList
import net.azurewebsites.athleet.workouts.WorkoutList

// Handles operations on Live Data and holds details about it.
class DataSource(resources: Resources) {
    @RequiresApi(Build.VERSION_CODES.O)
    private var initialWorkoutList = WorkoutList(resources)
    @RequiresApi(Build.VERSION_CODES.O)
    private val WorkoutsLiveData = MutableLiveData(initialWorkoutList)
    @RequiresApi(Build.VERSION_CODES.O)
    private val initialTeamsList = TeamsList(resources)
    @RequiresApi(Build.VERSION_CODES.O)
    private val TeamsLiveData = MutableLiveData(initialTeamsList)
    var currentWorkout: Workout? = null
    private var ExercisesLiveData:MutableLiveData<List<Exercise>> = MutableLiveData(listOf(Exercise(null,null,null,null,null,null)))

    @RequiresApi(Build.VERSION_CODES.O)
    fun addWorkouts(list: List<Workout>) {
        WorkoutsLiveData.postValue(list)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWorkoutList(): LiveData<List<Workout>> {
        return WorkoutsLiveData
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTeamsList(): LiveData<List<Team>> {
        return TeamsLiveData
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTeams(list: List<Team>) {
        TeamsLiveData.postValue(list)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTeam(newTeam: Team) {
        val currentList = TeamsLiveData.value
        if (currentList == null) {
            TeamsLiveData.postValue(listOf(newTeam))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, newTeam)
            TeamsLiveData.postValue(updatedList)
        }
    }

    fun getExerciseList(): LiveData<List<Exercise>> {
            return ExercisesLiveData
    }

    fun addExercise(newExercise: Exercise) {
        if(currentWorkout != null)
        {
            val currentList = ExercisesLiveData.value
            val updatedList=currentList!!.toMutableList()
                updatedList.add(0,newExercise)
                ExercisesLiveData.postValue(updatedList)
        }
    }
    fun clearExerciseList(){
        currentWorkout!!.exercises = ExercisesLiveData.value;
        ExercisesLiveData.postValue(null)
    }

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