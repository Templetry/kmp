package dev.template.kmplib.wire

import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class EnvelopeTest {

    @Test
    fun `an envelope survives a round trip`() {
        val json = Json { ignoreUnknownKeys = true }
        val original = Envelope(data = "hola", issuedAt = 1_700_000_000L)

        // The generic serializer is passed explicitly. Writing
        // encodeToString<Envelope<String>>(original) instead resolves to the
        // two-argument overload and reads the type argument as the serializer,
        // and the error it produces points nowhere near the real problem.
        val serializer = Envelope.serializer(String.serializer())

        val encoded = json.encodeToString(serializer, original)
        assertEquals(original, json.decodeFromString(serializer, encoded))
    }
}
