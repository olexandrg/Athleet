package net.azurewebsites.athleet.workouts

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.models.DataSource

class WorkoutsListViewModel(val dataSource: DataSource) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val workoutsLiveData = dataSource.getWorkoutList()

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertWorkouts(list:List<Workout>) {
        dataSource.addWorkouts(list)
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

