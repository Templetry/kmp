package dev.template.kmplib.config

/**
 * ADR-0018 profiles, as a library sees them.
 *
 * A library has no runtime environment of its own — the app that embeds it does.
 * What it does have is per-environment defaults its consumers would otherwise
 * each redeclare: a base URL, a log level, a feature gate. Selected at build
 * time with -PlibEnv and read back through [current].
 */
public enum class Profile {
    Development,
    Staging,
    Production;

    public companion object {
        /**
         * Kept separate from [current] so the mapping can be tested without a
         * rebuild. An unknown value falls back rather than throwing: a library
         * that dies on a typo in a build flag takes the consuming app down with
         * it at startup.
         */
        public fun from(raw: String): Profile = when (raw.lowercase()) {
            "staging" -> Staging
            "production" -> Production
            else -> Development
        }

        public val current: Profile get() = from(BuildProfile.ENVIRONMENT)
    }
}
