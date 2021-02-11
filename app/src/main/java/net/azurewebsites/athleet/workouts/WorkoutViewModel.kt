package net.azurewebsites.athleet.workouts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.network.AthleetApi
import net.azurewebsites.athleet.network.Exercise
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class WorkoutViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getExercises()
    }

    private fun getExercises() {

        viewModelScope.launch {
            try {
                var listResult = AthleetApi.retrofitService.getExercisesForWorkout(
                    getFirebaseTokenId(), "35")
                _response.value = "Success: ${listResult.size} Exercises retrieved"
            } catch (e: Exception){
                _response.value = "Failure: ${e.message}"
            }
        }


    }
}