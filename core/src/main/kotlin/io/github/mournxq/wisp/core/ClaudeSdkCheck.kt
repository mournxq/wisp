package io.github.mournxq.wisp.core

import com.anthropic.client.okhttp.AnthropicOkHttpClient
import com.anthropic.core.jsonMapper
import com.anthropic.models.messages.MessageCreateParams

object ClaudeSdkCheck {
    fun requestJson(): String {
        val client = AnthropicOkHttpClient.builder().apiKey("offline-check").build()
        val params = MessageCreateParams.builder()
            .model("claude-haiku-5-5")
            .maxTokens(1024L)
            .addUserMessage("Hallo")
            .build()
        client.close()
        return jsonMapper().writeValueAsString(params._body())
    }
}
