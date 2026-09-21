package com.jarvis.assistant.ui

import androidx.lifecycle.ViewModel
import com.jarvis.assistant.data.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _messages = MutableStateFlow(
        listOf(ChatMessage(0L, "JARVIS online. How can I help?", false))
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private var nextId = 1L

    fun sendMessage(text: String) {
        val clean = text.trim()
        if (clean.isEmpty()) return

        val userMessage = ChatMessage(nextId++, clean, true)
        val reply = ChatMessage(
            nextId++,
            "I received: \"$clean\". Command processing will be added in a later stage.",
            false
        )
        _messages.value = _messages.value + userMessage + reply
    }
}
