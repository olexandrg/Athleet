package net.azurewebsites.athleet.Teams

import android.util.Log
import androidx.lifecycle.ViewModel

data class Teammate(val name: String)

class TeamViewModel : ViewModel() {

    var teamName = "Team Name"
    var teamDescription = "This describes the team."
    var teamUsers = mutableListOf<Teammate>(
        Teammate("Nathan"),
        Teammate("Olex"),
        Teammate("Ryan"),
        Teammate("Simon"),
        Teammate("Taylor")
    )



    init {
        Log.i("TeamViewModel", "TeamViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("TeamViewModel", "TeamViewModel destroyed!")
    }

}