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

class WorkoutViewModel() : ViewModel() {

    var workoutId = -1

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>>
        get() = _exercises

    private val _navigateToSelectedExercise = MutableLiveData<Exercise>()
    val navigateToSelectedExercise: LiveData<Exercise>
        get() = _navigateToSelectedExercise

    init {
        getExercises(workoutId)
    }

    fun getExercises(workoutId: Int) {
        viewModelScope.launch {
            try {
                _exercises.value = AthleetApi.retrofitService.getExercisesForWorkout(
                    getFirebaseTokenId(), workoutId.toString())
                _response.value = "Success exercises retrieved"
            } catch (e: Exception){
                _response.value = "Failure: ${e.message}"
            }
        }
    }



    fun displayExerciseDetails(exercise: Exercise) {
        _navigateToSelectedExercise.value = exercise
    }

    fun displayExerciseDetailsComplete() {
        _navigateToSelectedExercise.value = null
    }
}