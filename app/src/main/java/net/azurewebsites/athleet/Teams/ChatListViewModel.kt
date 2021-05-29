package net.azurewebsites.athleet.Teams

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.athleet.models.Conversation
import net.azurewebsites.athleet.models.DataSource
import net.azurewebsites.athleet.models.Team
import net.azurewebsites.athleet.models.UserConvs

class ChatListViewModel(val dataSource: DataSource) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val chatLiveData = dataSource.getChatList()

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertChat(list:List<UserConvs>) {
        dataSource.addChats(list)
    }
}

class ChatListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatListViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}