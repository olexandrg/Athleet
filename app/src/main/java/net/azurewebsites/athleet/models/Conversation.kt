package net.azurewebsites.athleet.models

import java.util.*

data class Conversation(
    val MessageID: Int,
    val ConversationID: Int,
    var MessageDate: Date,
    var MessageContent: String,
    val UserName: String
    )