package io.github.mournxq.wisp.core

import com.anthropic.client.okhttp.AnthropicOkHttpClient

data class ModelOption(val id: String, val name: String)

object ModelCatalog {
    const val DEFAULT_MODEL = "claude-haiku-5-5"

    private val supported = Regex("^claude-(haiku|sonnet|opus|fable|mythos)-5(-\\d+)?$")
    private val familyOrder = listOf("haiku", "sonnet", "opus", "fable", "mythos")

    fun selectable(models: List<ModelOption>): List<ModelOption> =
        models.filter { supported.matches(it.id) }
            .sortedWith(compareBy<ModelOption> { familyOrder.indexOf(it.id.split("-")[1]) }.thenByDescending { it.id })

    fun fetch(apiKey: String): Result<List<ModelOption>> = runCatching {
        val client = AnthropicOkHttpClient.builder().apiKey(apiKey).build()
        try {
            selectable(client.models().list().autoPager().map { ModelOption(it.id(), it.displayName()) })
        } finally {
            client.close()
        }
    }
}
