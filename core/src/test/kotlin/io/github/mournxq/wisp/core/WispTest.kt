package io.github.mournxq.wisp.core

import kotlin.test.Test
import kotlin.test.assertEquals

class WispTest {
    @Test
    fun greetingContainsName() {
        assertEquals("Hallo, ich bin Wisp.", Wisp.greeting())
    }
}
