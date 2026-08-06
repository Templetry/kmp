package dev.template.kmpbase.ui.auth

import dev.template.kmpbase.core.common.mvi.MVIBaseIntent

sealed interface AuthIntent : MVIBaseIntent {
    data class Login(val token: String) : AuthIntent
    data object LoginAnonymous          : AuthIntent
    data object Logout                  : AuthIntent
    data object CheckAuth               : AuthIntent
    data object ClearError              : AuthIntent
}
