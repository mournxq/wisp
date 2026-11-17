package io.github.mournxq.wisp.core

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertContains

class SystemPromptTest {
    @Test
    fun containsGermanDate() {
        assertContains(SystemPrompt.build(LocalDate.of(2026, 11, 17)), "Heute ist Dienstag, 17. November 2026.")
    }
}
