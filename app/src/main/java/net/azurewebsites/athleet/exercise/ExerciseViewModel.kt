package net.azurewebsites.athleet.exercise

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.azurewebsites.athleet.models.Exercise

class ExerciseViewModel(exercise: Exercise, app: Application) : AndroidViewModel(app) {

    private val _selectedExercise = MutableLiveData<Exercise>()
    val selectedExercise: LiveData<Exercise>
        get() = _selectedExercise

    init {
        _selectedExercise.value = exercise
    }
}