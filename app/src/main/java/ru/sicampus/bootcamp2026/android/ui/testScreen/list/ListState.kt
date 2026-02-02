package ru.sicampus.bootcamp2026.android.ui.testScreen.list

import ru.sicampus.bootcamp2026.android.domain.entities.UserEntity

sealed interface ListState {
    data class Error(
        val reason: String
    ): ListState
    data object Loading: ListState
    data class Content(
        val users: List<UserEntity>
    ): ListState
}