package dev.template.kmplib

/**
 * The library's entry point.
 *
 * A shared library exists to hold the things every consumer must agree on —
 * types, invariants, and the rules that would otherwise be reimplemented once
 * per platform and drift apart. Anything that only one app needs does not
 * belong here.
 */
public object KMPLibrary {

    public const val NAME: String = "kmplib"

    /** Proves the expect/actual wiring reaches every target. */
    public fun describe(): String = "$NAME on ${platformName()}"
}
