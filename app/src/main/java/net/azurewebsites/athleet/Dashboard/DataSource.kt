package net.azurewebsites.athleet.Dashboard

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.azurewebsites.athleet.Workouts.Workout
import net.azurewebsites.athleet.Workouts.WorkoutList

/* Handles operations on WorkoutsLiveData and holds details about it. */
class DataSource(resources: Resources) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val initialWorkoutList = WorkoutList(resources)
    @RequiresApi(Build.VERSION_CODES.O)
    private val WorkoutsLiveData = MutableLiveData(initialWorkoutList)

    /* Adds Workout to liveData and posts value. */
    @RequiresApi(Build.VERSION_CODES.O)
    fun addWorkout(workout: Workout) {
        val currentList = WorkoutsLiveData.value
        if (currentList == null) {
            WorkoutsLiveData.postValue(listOf(workout))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, workout)
            WorkoutsLiveData.postValue(updatedList)
        }
    }

    /* Removes Workout from liveData and posts value. */
    @RequiresApi(Build.VERSION_CODES.O)
    fun removeWorkout(Workout: Workout) {
        val currentList = WorkoutsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(Workout)
            WorkoutsLiveData.postValue(updatedList)
        }
    }

    /* Returns Workout given an ID. */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getWorkoutForName(name: String): Workout? {
        WorkoutsLiveData.value?.let { Workouts ->
            return Workouts.firstOrNull{ it.name == name}
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWorkoutList(): LiveData<List<Workout>> {
        return WorkoutsLiveData
    }

/*    *//* Returns a random Workout asset for Workouts that are added. *//*
    fun getRandomWorkoutImageAsset(): Int? {
        val randomNumber = (initialWorkoutList.indices).random()
        return initialWorkoutList[randomNumber].image
    }*/

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}