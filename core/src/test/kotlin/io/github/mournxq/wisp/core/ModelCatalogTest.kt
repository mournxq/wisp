package io.github.mournxq.wisp.core

import kotlin.test.Test
import kotlin.test.assertEquals

class ModelCatalogTest {
    @Test
    fun keepsOnlyCurrentModelsSortedByFamily() {
        val fromApi = listOf(
            ModelOption("claude-opus-5-5", "Claude Opus 5.5"),
            ModelOption("claude-haiku-4-5", "Claude Haiku 4.5"),
            ModelOption("claude-fable-5-2", "Claude Fable 5.2"),
            ModelOption("claude-haiku-5-5", "Claude Haiku 5.5"),
            ModelOption("claude-opus-5", "Claude Opus 5"),
            ModelOption("claude-sonnet-5-5", "Claude Sonnet 5.5"),
            ModelOption("claude-opus-4-8", "Claude Opus 4.8"),
        )
        assertEquals(
            listOf("claude-haiku-5-5", "claude-sonnet-5-5", "claude-opus-5-5", "claude-opus-5", "claude-fable-5-2"),
            ModelCatalog.selectable(fromApi).map { it.id },
        )
    }
}
