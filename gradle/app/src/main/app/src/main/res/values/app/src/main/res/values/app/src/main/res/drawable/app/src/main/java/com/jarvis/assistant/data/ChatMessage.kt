package com.jarvis.assistant.data

data class ChatMessage(
    val id: Long,
    val text: String,
    val isUser: Boolean
)
