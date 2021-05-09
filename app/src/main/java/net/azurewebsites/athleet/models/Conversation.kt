package net.azurewebsites.athleet.models

import java.util.*

data class Conversation(
    val MessageID: Int,
    val ConversationID: Int,
    var MessageDate: String,
    var MessageContent: String,
    val UserName: String
    )