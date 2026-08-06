package dev.template.kmpbase.domain.usecases.user

import dev.template.kmpbase.core.common.utils.AppResult
import dev.template.kmpbase.data.models.User
import dev.template.kmpbase.data.repository.IUserRepository
import dev.template.kmpbase.domain.usecases.NoParamUseCase

class GetMeUseCase(private val repository: IUserRepository) : NoParamUseCase<User>() {
    override suspend fun execute(): AppResult<User> = repository.getMe()
}
