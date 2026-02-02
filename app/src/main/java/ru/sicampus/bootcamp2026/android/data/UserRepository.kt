package ru.sicampus.bootcamp2026.android.data

import ru.sicampus.bootcamp2026.android.data.source.UserInfoDataSource
import ru.sicampus.bootcamp2026.android.domain.entities.UserEntity

class UserRepository(
    private val userInfoDataSource: UserInfoDataSource
) {
    suspend fun getUsers(): Result<List<UserEntity>>{
        return userInfoDataSource.getUser().map{ listDTO ->
            listDTO.mapNotNull{ userDTO ->
               UserEntity (
                   name = userDTO.name ?: return@mapNotNull null,
                   email = userDTO.email ?: return@mapNotNull null,
                   photoUrl = userDTO.photoUrl ?: return@mapNotNull null
                   )

            }

        }
    }
}