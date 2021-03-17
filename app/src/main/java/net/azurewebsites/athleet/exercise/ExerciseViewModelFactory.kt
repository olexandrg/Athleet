package net.azurewebsites.athleet.exercise

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.models.Exercise

class ExerciseViewModelFactory (
    private val exercise: Exercise,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
                return ExerciseViewModel(exercise, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}