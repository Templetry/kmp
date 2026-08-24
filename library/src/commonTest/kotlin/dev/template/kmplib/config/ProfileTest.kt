package dev.template.kmplib.config

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProfileTest {

    @Test
    fun `the build profile is one of the three`() {
        assertTrue(Profile.current in Profile.entries)
    }

    @Test
    fun `profiles are read case-insensitively`() {
        assertEquals(Profile.Staging, Profile.from("STAGING"))
        assertEquals(Profile.Production, Profile.from("Production"))
    }

    @Test
    fun `an unknown profile falls back to development rather than throwing`() {
        // A library that dies on a typo in a build flag takes the consuming app
        // down with it at startup.
        assertEquals(Profile.Development, Profile.from("no-such-profile"))
        assertEquals(Profile.Development, Profile.from(""))
    }
}
