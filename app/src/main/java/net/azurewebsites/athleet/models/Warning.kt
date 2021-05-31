package net.azurewebsites.athleet.models

data class Warning (
    val userId: Int?,
    var warningMessage: String = "You have been warned for chat misuse."
)