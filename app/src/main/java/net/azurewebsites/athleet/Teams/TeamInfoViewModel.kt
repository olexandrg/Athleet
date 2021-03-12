package net.azurewebsites.athleet.Teams

import android.content.Context
import android.os.Build
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.Dashboard.DataSource

class TeamInfoViewModel(val dataSource: DataSource) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    val teamInfoLiveData = dataSource.getTeamInfo()
}

class TeamMembersListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamInfoViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}