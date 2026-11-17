package io.github.mournxq.wisp.core

import kotlin.test.Test
import kotlin.test.assertContains

class ClaudeSdkCheckTest {
    @Test
    fun requestJsonContainsModelAndMessage() {
        val json = ClaudeSdkCheck.requestJson()
        assertContains(json, "\"model\":\"claude-haiku-5-5\"")
        assertContains(json, "Hallo")
    }
}
