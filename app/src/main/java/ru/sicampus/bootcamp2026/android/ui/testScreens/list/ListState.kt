package ru.sicampus.bootcamp2026.android.ui.testScreens.list

import ru.sicampus.bootcamp2026.android.domain.list.entities.UserEntity


sealed interface ListState {
    data class Error(val reason: String): ListState
    data object Loading: ListState
    data class Content(
        val users: List<UserEntity>
    ): ListState
}