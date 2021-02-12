package net.azurewebsites.athleet.workouts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.network.AthleetApi
import net.azurewebsites.athleet.network.Exercise

// Need to get this from the previous fragment for the api service call
const val workoutID = "34"

class WorkoutViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>>
        get() = _exercises

    init {
        getExercises()
    }

    private fun getExercises() {

        viewModelScope.launch {
            try {
                _exercises.value = AthleetApi.retrofitService.getExercisesForWorkout(
                    getFirebaseTokenId(), workoutID)
                _response.value = "Success Exercises retrieved"
            } catch (e: Exception){
                _response.value = "Failure: ${e.message}"
            }
        }


    }
}