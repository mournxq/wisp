package io.github.mournxq.wisp.core

import com.anthropic.client.okhttp.AnthropicOkHttpClient
import com.anthropic.models.messages.MessageCreateParams
import com.anthropic.models.messages.MessageParam
import com.anthropic.models.messages.OutputConfig
import com.anthropic.models.messages.StopReason
import java.time.Duration
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

sealed interface ChatResult {
    data class Reply(val text: String) : ChatResult
    data class Failed(val error: ChatError) : ChatResult
}

class ClaudeChat(apiKey: String) : AutoCloseable {
    private val client = AnthropicOkHttpClient.builder()
        .apiKey(apiKey)
        .timeout(Duration.ofSeconds(60))
        .build()
    private val history = mutableListOf<MessageParam>()

    fun send(text: String, model: String, today: LocalDate = LocalDate.now()): ChatResult {
        val userMessage = MessageParam.ofUser(text)
        val params = MessageCreateParams.builder()
            .model(model)
            .maxTokens(16000L)
            .system(SystemPrompt.build(today))
            .outputConfig(OutputConfig.builder().effort(OutputConfig.Effort.LOW).build())
            .messages(history + userMessage)
            .build()
        val response = try {
            client.messages().create(params)
        } catch (e: Exception) {
            return ChatResult.Failed(ChatError.from(e))
        }
        if (response.stopReason().getOrNull() == StopReason.REFUSAL) {
            return ChatResult.Failed(ChatError.Refused)
        }
        history += userMessage
        history += response.toParam()
        val reply = response.content().mapNotNull { it.text().getOrNull()?.text() }.joinToString("").trim()
        return ChatResult.Reply(reply)
    }

    fun clear() = history.clear()

    override fun close() = client.close()
}
