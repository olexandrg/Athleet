package net.azurewebsites.athleet.Teams

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.dataSource
import net.azurewebsites.athleet.getFirebaseTokenId
import net.azurewebsites.athleet.models.DataSource
import net.azurewebsites.athleet.models.Workout
import net.azurewebsites.athleet.workouts.WorkoutsListViewModel

class TeamWorkoutListViewModel (val dataSource: DataSource) : ViewModel() {
    private val api = Api.createSafe()

    @RequiresApi(Build.VERSION_CODES.O)
    val workoutsLiveData = dataSource.getTeamWorkoutList()

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertWorkouts(teamName: String) {
        viewModelScope.launch {
            try{
                var token = getFirebaseTokenId()
                var teamId = api.getTeamId(token, teamName)
                Log.i("TEST", "TeamID ${teamId.toString()}")
                var list = api.getTeamWorkouts(token, teamId)
                Log.i("TEST", "Workouts ${list.toString()}")
                dataSource.addTeamWorkouts(list)
            }catch (e: Exception){
                Log.i("TEST", "failed insert workouts in team")
            }
        }
    }


}


class TeamWorkoutListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamWorkoutListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamWorkoutListViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}