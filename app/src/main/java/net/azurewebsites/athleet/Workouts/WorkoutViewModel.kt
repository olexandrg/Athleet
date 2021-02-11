package net.azurewebsites.athleet.Workouts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getExercises()
    }

    private fun getExercises() {
        _response.value = "Set the exercise api here"
    }
}