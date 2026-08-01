package dev.template.kmpbase.domain.usecases.user

import dev.template.kmpbase.core.common.utils.AppResult
import dev.template.kmpbase.data.models.User
import dev.template.kmpbase.data.repository.IUserRepository
import dev.template.kmpbase.domain.usecases.UseCase

class UpdateProfileUseCase(private val repository: IUserRepository) : UseCase<User, User>() {
    override suspend fun execute(params: User): AppResult<User> = repository.updateProfile(params)
}
