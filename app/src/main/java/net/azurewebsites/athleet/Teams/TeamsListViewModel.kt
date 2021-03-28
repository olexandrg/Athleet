package net.azurewebsites.athleet.Teams

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.models.DataSource

class TeamsListViewModel(val dataSource: DataSource) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val teamsLiveData = dataSource.getTeamsList()

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertTeams(list:List<Team>) {
        dataSource.addTeams(list)
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