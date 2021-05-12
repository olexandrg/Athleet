package net.azurewebsites.athleet.Teams

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.models.DataSource
import net.azurewebsites.athleet.models.TeamUser
import net.azurewebsites.athleet.models.Workout

class TeamMembersListViewModel(val dataSource: DataSource)  : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    val teamMembersLiveData = dataSource.getTeamMembersList()

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertTeamMembers(list:List<TeamUser>) {
        dataSource.setTeamList(list)
    }
}
class TeamMembersListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamMembersListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamMembersListViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
