package ru.sicampus.bootcamp2026.android.domain.list

import ru.sicampus.bootcamp2026.android.data.UserRepository
import ru.sicampus.bootcamp2026.android.domain.list.entities.UserEntity

class GetUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<UserEntity>> {
        return userRepository.getUsers()
    }
}