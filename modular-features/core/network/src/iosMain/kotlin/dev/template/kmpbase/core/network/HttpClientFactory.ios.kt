package dev.template.kmpbase.core.network

import io.ktor.client.engine.darwin.Darwin

actual fun httpEngineFactory() = Darwin
