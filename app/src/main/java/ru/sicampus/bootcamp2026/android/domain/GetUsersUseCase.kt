package ru.sicampus.bootcamp2026.android.domain

import ru.sicampus.bootcamp2026.android.data.AppRepository
import ru.sicampus.bootcamp2026.android.domain.entities.UserEntity

class GetUsersUseCase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): Result<List<UserEntity>> =
        repository.getUsers()
}
