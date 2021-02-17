package net.azurewebsites.athleet.exercise
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.Dashboard.DataSource

class ExerciseListViewModel(val dataSource: DataSource) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val exercisesLiveData = dataSource.getExerciseList()

    /* If the name and description are present, create new Exercise and add it to the datasource */
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertExercise(exerciseName: String?, exerciseDescription: String?, reps:Int, sets:Int, unitType:String, unitCount:Float) {
        if (exerciseName == null || exerciseDescription == null) { return }
        val newExercise = Exercise(
            exerciseName,exerciseDescription,reps,sets,unitType,unitCount)
        dataSource.addExercise(newExercise)
    }
}

class ExerciseListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExerciseListViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}