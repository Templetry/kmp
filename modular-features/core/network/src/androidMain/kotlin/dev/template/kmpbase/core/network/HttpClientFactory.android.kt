package dev.template.kmpbase.core.network

import io.ktor.client.engine.okhttp.OkHttp

actual fun httpEngineFactory() = OkHttp
