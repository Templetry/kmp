package dev.template.kmpbase.core.common.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <T> Flow<T>.mapToResult(): Flow<dev.template.kmpbase.core.common.utils.AppResult<T>> =
    map { dev.template.kmpbase.core.common.utils.AppResult.Success(it) as dev.template.kmpbase.core.common.utils.AppResult<T> }
        .catch { emit(dev.template.kmpbase.core.common.utils.AppResult.Error(it.message ?: "Error")) }
