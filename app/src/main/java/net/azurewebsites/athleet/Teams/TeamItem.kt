package net.azurewebsites.athleet.Teams
import kotlin.IndexOutOfBoundsException

data class TeamItem (
    val teamId: Int?,
    var teamName: String?,
    var teamDescription: String?
)

interface teamHandler {
    companion object {
        // returns team object
        fun returnTeam(list: List<TeamItem>, teamName: String): TeamItem {
            return list?.filter { it.teamName == teamName }?.get(0)
        }
        @Throws (IndexOutOfBoundsException::class)
        // returns team name, if found
        fun returnTeamName(list: List<TeamItem>?, teamName: String): String {
            return try {
                val response = list?.filter {it.teamName == teamName}?.get(0)?.teamName?.toString()
                if (response == "kotlin.Unit") throw IndexOutOfBoundsException()
                response.toString()
            } catch(e: IndexOutOfBoundsException) {
                "Team $teamName not found."
            }
        }

        // returns team ID
        fun returnTeamID(list: List<TeamItem>?, teamName: String): Int? {
            return try {
                list?.filter {it.teamName == teamName}?.get(0)?.teamId

            } catch(e: IndexOutOfBoundsException) {
                0
            }
        }
    }
}