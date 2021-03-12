package net.azurewebsites.athleet.Teams

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.Dashboard.DataSource

class TeamMembersListViewModel(val dataSource: DataSource) : ViewModel() {

}

class TeamMembersListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamMembersListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamMembersListViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}