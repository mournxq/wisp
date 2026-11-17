package io.github.mournxq.wisp.core

import com.anthropic.errors.AnthropicIoException
import com.anthropic.errors.AnthropicServiceException

sealed interface ChatError {
    data object NoKey : ChatError
    data object WrongKey : ChatError
    data object NoCredits : ChatError
    data object RateLimited : ChatError
    data object Overloaded : ChatError
    data object Offline : ChatError
    data object Refused : ChatError
    data class Other(val detail: String) : ChatError

    companion object {
        fun classify(status: Int, type: String?, message: String?): ChatError = when {
            status == 402 || type == "billing_error" ||
                message.orEmpty().contains("credit balance", ignoreCase = true) -> NoCredits
            status == 401 || status == 403 ||
                type == "authentication_error" || type == "permission_error" -> WrongKey
            status == 429 || type == "rate_limit_error" -> RateLimited
            status >= 500 || type == "overloaded_error" -> Overloaded
            else -> Other(message ?: "HTTP $status")
        }

        fun from(error: Throwable): ChatError = when (error) {
            is AnthropicServiceException -> classify(
                error.statusCode(),
                error.type().map { it.asString() }.orElse(null),
                error.message,
            )
            is AnthropicIoException -> Offline
            else -> Other(error.message ?: error.javaClass.simpleName)
        }
    }
}
