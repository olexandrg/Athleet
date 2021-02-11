package net.azurewebsites.athleet.workouts
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.Dashboard.DataSource

class WorkoutsListViewModel(val dataSource: DataSource) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val workoutsLiveData = dataSource.getWorkoutList()

    /* If the name and description are present, create new Workout and add it to the datasource */
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertWorkout(workoutName: String?, workoutDescription: String?) {
        if (workoutName == null || workoutDescription == null) { return }
        val newWorkout = Workout(
            name = workoutName,
            //lastCompleted = Date.from(Instant.now()),
            description = workoutDescription,
                    exercises = null)
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