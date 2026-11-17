package io.github.mournxq.wisp

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.mournxq.wisp.core.ChatError
import io.github.mournxq.wisp.core.ChatResult
import io.github.mournxq.wisp.core.ClaudeChat
import io.github.mournxq.wisp.core.ModelCatalog
import io.github.mournxq.wisp.core.ModelOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ChatLine(val fromUser: Boolean, val text: String, val error: ChatError? = null)

sealed interface KeyCheck {
    data object Idle : KeyCheck
    data object Checking : KeyCheck
    data object Ok : KeyCheck
    data class Failed(val error: ChatError) : KeyCheck
}

class WispViewModel(application: Application) : AndroidViewModel(application) {
    private val settings = SettingsStore(application)
    private var chat: ClaudeChat? = settings.loadApiKey()?.let(::ClaudeChat)

    val lines = mutableStateListOf<ChatLine>()
    var busy by mutableStateOf(false)
        private set
    var showSettings by mutableStateOf(chat == null)
        private set
    var hasKey by mutableStateOf(chat != null)
        private set
    var model by mutableStateOf(settings.model)
        private set
    var models by mutableStateOf(emptyList<ModelOption>())
        private set
    var keyCheck by mutableStateOf<KeyCheck>(KeyCheck.Idle)
        private set

    fun send(text: String) {
        val message = text.trim()
        if (message.isEmpty() || busy) return
        lines += ChatLine(fromUser = true, text = message)
        val current = chat
        if (current == null) {
            lines += ChatLine(fromUser = false, text = "", error = ChatError.NoKey)
            return
        }
        busy = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { current.send(message, model) }
            lines += when (result) {
                is ChatResult.Reply -> ChatLine(fromUser = false, text = result.text)
                is ChatResult.Failed -> ChatLine(fromUser = false, text = "", error = result.error)
            }
            busy = false
        }
    }

    fun newChat() {
        chat?.clear()
        lines.clear()
    }

    fun saveKey(apiKey: String) {
        val key = apiKey.trim()
        if (key.isEmpty()) return
        settings.saveApiKey(key)
        replaceChat(ClaudeChat(key))
        hasKey = true
        loadModels(key)
    }

    fun deleteKey() {
        settings.deleteApiKey()
        replaceChat(null)
        hasKey = false
        models = emptyList()
        keyCheck = KeyCheck.Idle
    }

    fun selectModel(id: String) {
        settings.model = id
        model = id
    }

    fun openSettings() {
        showSettings = true
        if (models.isEmpty()) settings.loadApiKey()?.let(::loadModels)
    }

    fun closeSettings() {
        showSettings = false
    }

    private fun loadModels(apiKey: String) {
        keyCheck = KeyCheck.Checking
        viewModelScope.launch {
            withContext(Dispatchers.IO) { ModelCatalog.fetch(apiKey) }
                .onSuccess {
                    models = it
                    keyCheck = KeyCheck.Ok
                }
                .onFailure { keyCheck = KeyCheck.Failed(ChatError.from(it)) }
        }
    }

    private fun replaceChat(newChat: ClaudeChat?) {
        chat?.close()
        chat = newChat
        lines.clear()
    }

    override fun onCleared() {
        chat?.close()
    }
}
