package net.azurewebsites.athleet.models

import java.util.*

data class Conversation(
    val messageID: Int,
    val conversationID: Int,
    var messageDate: String,
    var messageContent: String,
    val userName: String
    )