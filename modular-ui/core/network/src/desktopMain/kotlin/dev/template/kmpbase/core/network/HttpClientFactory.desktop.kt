package dev.template.kmpbase.core.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.java.Java

actual fun httpEngineFactory(): HttpClientEngineFactory<*> = Java
