package dev.template.kmpbase.feature.profile

import dev.template.kmpbase.core.common.mvi.MVIBaseIntent
import dev.template.kmpbase.data.models.User

sealed interface ProfileIntent : MVIBaseIntent {
    data object Load                          : ProfileIntent
    data class  Save(val user: User)          : ProfileIntent
    data object ClearSuccess                  : ProfileIntent
    data object ClearError                    : ProfileIntent
}
