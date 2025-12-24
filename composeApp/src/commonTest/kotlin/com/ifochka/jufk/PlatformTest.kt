package com.ifochka.jufk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PlatformTest {
    @Test
    fun `supported platforms count is 6`() {
        assertEquals(6, Platform.getPlatformCount())
    }

    @Test
    fun `Android is a supported platform`() {
        assertTrue(Platform.isSupported("Android"))
    }

    @Test
    fun `iOS is a supported platform case insensitive`() {
        assertTrue(Platform.isSupported("ios"))
        assertTrue(Platform.isSupported("IOS"))
        assertTrue(Platform.isSupported("iOS"))
    }

    @Test
    fun `unknown platform is not supported`() {
        assertFalse(Platform.isSupported("Blackberry"))
    }
}
