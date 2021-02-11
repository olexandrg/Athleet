package net.azurewebsites.athleet.workouts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.azurewebsites.athleet.network.AthleetApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val tokenID = "Bearer " + "eyJhbGciOiJSUzI1NiIsImtpZCI6IjJjMmVkODQ5YThkZTI3ZTI0NjFlNGJjM2VmMDZhYzdhYjc4OGQyMmIiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiTmF0aGFuIFRvdXQiLCJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXRobGVldC03ODJhZSIsImF1ZCI6ImF0aGxlZXQtNzgyYWUiLCJhdXRoX3RpbWUiOjE2MTMwNzE1ODUsInVzZXJfaWQiOiJoRmtDNHJBd1owVUxMcDhUUldodGVkc2tDNWoxIiwic3ViIjoiaEZrQzRyQXdaMFVMTHA4VFJXaHRlZHNrQzVqMSIsImlhdCI6MTYxMzA3MTU4NSwiZXhwIjoxNjEzMDc1MTg1LCJlbWFpbCI6Im5hdGUudG91dEBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJuYXRlLnRvdXRAZ21haWwuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.AkNVxNt7hRrxJwtPyF4_wscc1yPz5ABVWQPcNJPuSiA3NAI6EwVamW3fkMzJzzJTXDp_UgUxz35CTN0IdcN-o0T1Ss1Jrev9wCqiN1wUjHijt_locljVln9Rdzyi8aQFsw17i9HA4zeG3zt67vxULtSxkM7TJFXYzMoPTL5e55MAnMUGKQeffhvhsZ46JfkDGf3K5i_0vjM33zHIJG1AOAUJPe5AC_kKoVPSzYRJ-SV1Cp0m2RpT12X6v2ijviG1TgnIu5-riuFexmlVzSmktTVokJIg5bYsqyIiWUgTMGnlEZSxinP9CyJTSYAKl7C-cIux82JWilti_GUOz0jL1Q"
class WorkoutViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getExercises()
    }

    private fun getExercises() {

        AthleetApi.retrofitService.getExercisesForWorkout(tokenID).enqueue(
            object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "Failure: "
                    Log.i("API CALL", "onFailure was entered")
                    Log.i("API CALL", t.message.toString())

                }

                override fun onResponse(call: Call<String>, response: Response<String>
                ) {
                    _response.value = response.body().toString()
                    Log.i("API CALL", "onResponse was entered")
                    Log.i("API CALL", response.body().toString())
                    Log.i("API CALL", response.code().toString())
                }
            }
        )


    }
}