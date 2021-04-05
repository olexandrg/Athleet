package net.azurewebsites.athleet.exercise

import android.app.Application
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.Exercise
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseViewModel(exercise: Exercise, app: Application) : AndroidViewModel(app) {

    private val _selectedExercise = MutableLiveData<Exercise>()
    val selectedExercise: LiveData<Exercise>
        get() = _selectedExercise

    private var id: Int
    private val api = Api.createSafe()

    init {
        _selectedExercise.value = exercise
        id = exercise.exerciseId
    }

    fun updateExercise(
        exerciseName: String,
        description: String,
        measureUnits: String,
        defaultReps: Int,
        exerciseSets: Int,
        unitCount: Int)
    {
        val exercise: Exercise = Exercise(id, exerciseName, description, measureUnits, defaultReps, exerciseSets, unitCount)
        Log.i("updateExercise", "this function got called")
        Log.i("updateExercise", exercise.toString())
        
        api.updateExercise(getFirebaseTokenId(), id, exercise).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("updateExercise", response.code().toString())
                Log.i("updateExercise", response.toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("updateExercise", call.toString())
                Log.i("updateExercise", t.toString())
            }
        })


    }

}