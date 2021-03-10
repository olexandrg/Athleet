package net.azurewebsites.athleet.Teams

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.Dashboard.DataSource
import net.azurewebsites.athleet.workouts.Workout

class TeamsListViewModel(val dataSource: DataSource) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val teamsLiveData = dataSource.getTeamsList()

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertTeams(list:List<Team>) {
        dataSource.addTeams(list)
    }
    // If the name and description are present, create new Team and add it to the datasource
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertTeam(teamName: String?, teamDescription: String?) {
        if (teamName == null || teamDescription == null) { return }
        val newTeam = Team(
            5,teamName = teamName, teamDescription = teamDescription
        )
        dataSource.addTeam(newTeam)
    }
}

class TeamsListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamsListViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}