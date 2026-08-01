package dev.template.kmpbase.feature.profile

import dev.template.kmpbase.core.common.mvi.MVIBaseState
import dev.template.kmpbase.data.models.User

data class ProfileState(
    val user      : User?   = null,
    val isLoading : Boolean = false,
    val isSaving  : Boolean = false,
    val saveSuccess: Boolean = false,
    val error     : String? = null,
) : MVIBaseState
