package net.azurewebsites.athleet.Dashboard

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.Workouts.Workout
import java.util.*

//import androidx.preference.PreferenceManager


    class WorkoutsListViewModel(val dataSource: DataSource) : ViewModel() {

        @RequiresApi(Build.VERSION_CODES.O)
        val workoutsLiveData = dataSource.getWorkoutList()

        /* If the name and description are present, create new Workout and add it to the datasource */
        @RequiresApi(Build.VERSION_CODES.O)
        fun insertWorkout(WorkoutName: String?, WorkoutDescription: String?) {
            if (WorkoutName == null || WorkoutDescription == null) {
                return
            }

            //val image = dataSource.getRandomWorkoutImageAsset()
            val newWorkout = Workout(
                WorkoutName,
                Date(),
                WorkoutDescription
            )

            dataSource.addWorkout(newWorkout)
        }
    }

    class WorkoutsListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WorkoutsListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WorkoutsListViewModel(
                    dataSource = DataSource.getDataSource(context.resources)
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

