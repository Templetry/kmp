package dev.template.kmplib

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertTrue

class KMPLibraryTest {

    @Test
    fun `describe reaches the platform actual`() {
        // If expect/actual is wired wrongly this does not fail at runtime, it
        // fails to compile on exactly one target — which is why it is asserted
        // on every target rather than only on the JVM.
        assertContains(KMPLibrary.describe(), KMPLibrary.NAME)
        assertTrue(KMPLibrary.describe().length > KMPLibrary.NAME.length)
    }
}
