package dev.template.kmplib.wire

import kotlinx.serialization.Serializable

/**
 * A serialisable type, present so the serialization plugin is exercised by
 * something real rather than merely configured.
 *
 * Wire types are the strongest argument for a shared library: when the same
 * shape is declared once here instead of once per platform, a field that
 * changes cannot silently mean two different things on Android and iOS.
 */
@Serializable
public data class Envelope<T>(
    val data: T,
    val issuedAt: Long,
)
