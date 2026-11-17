package io.github.mournxq.wisp.core

import com.anthropic.errors.AnthropicIoException
import kotlin.test.Test
import kotlin.test.assertEquals

class ChatErrorTest {
    @Test
    fun wrongKey() {
        assertEquals(ChatError.WrongKey, ChatError.classify(401, "authentication_error", "invalid x-api-key"))
        assertEquals(ChatError.WrongKey, ChatError.classify(403, "permission_error", null))
    }

    @Test
    fun noCredits() {
        assertEquals(ChatError.NoCredits, ChatError.classify(402, "billing_error", null))
        assertEquals(
            ChatError.NoCredits,
            ChatError.classify(400, "invalid_request_error", "Your credit balance is too low to access the Anthropic API."),
        )
    }

    @Test
    fun rateLimitedAndOverloaded() {
        assertEquals(ChatError.RateLimited, ChatError.classify(429, "rate_limit_error", null))
        assertEquals(ChatError.Overloaded, ChatError.classify(529, "overloaded_error", null))
        assertEquals(ChatError.Overloaded, ChatError.classify(500, "api_error", null))
    }

    @Test
    fun otherKeepsMessage() {
        assertEquals(ChatError.Other("bad request"), ChatError.classify(400, "invalid_request_error", "bad request"))
        assertEquals(ChatError.Other("HTTP 404"), ChatError.classify(404, null, null))
    }

    @Test
    fun networkFailureIsOffline() {
        assertEquals(ChatError.Offline, ChatError.from(AnthropicIoException("Unable to resolve host")))
    }
}
