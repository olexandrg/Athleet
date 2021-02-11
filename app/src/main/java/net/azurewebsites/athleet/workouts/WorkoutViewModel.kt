package net.azurewebsites.athleet.workouts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.network.AthleetApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WorkoutViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getExercises()
    }

    private fun getExercises() {

        AthleetApi.retrofitService.getExercisesForWorkout(getFirebaseTokenId(), "35").enqueue(
            object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                    Log.i("API CALL", "onFailure was entered")
                }

                override fun onResponse(call: Call<String>, response: Response<String>
                ) {
                    _response.value = response.body().toString()
                    Log.i("API CALL", "onResponse was entered")
                    Log.i("API CALL", response.body().toString())
                }
            }
        )


    }
}