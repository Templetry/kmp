package dev.template.kmpbase.feature.home

import dev.template.kmpbase.core.common.mvi.MVIBaseIntent

sealed interface HomeIntent : MVIBaseIntent {
    data object Load       : HomeIntent
    data object Refresh    : HomeIntent
    data object ClearError : HomeIntent
}
