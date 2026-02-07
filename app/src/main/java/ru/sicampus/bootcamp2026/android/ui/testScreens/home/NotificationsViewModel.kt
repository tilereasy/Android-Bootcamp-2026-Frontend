package ru.sicampus.bootcamp2026.android.ui.testScreens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2026.android.data.InvitationsRepository
import ru.sicampus.bootcamp2026.android.data.dto.InvitationWithMeetingDto
import ru.sicampus.bootcamp2026.android.data.source.InvitationsNetworkDataSource

data class NotificationsUiState(
    val items: List<InvitationWithMeetingDto> = emptyList(),
    val page: Int = 0,
    val isLast: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null
)

class NotificationsViewModel : ViewModel() {

    private val repo = InvitationsRepository(InvitationsNetworkDataSource())
    private val pageSize = 20
    private val status = "PENDING"

    private val _state = MutableStateFlow(NotificationsUiState())
    val state = _state.asStateFlow()

    fun loadFirstPage() {
        _state.value = NotificationsUiState(isLoading = true)

        viewModelScope.launch {
            val result = repo.getMyInvitationsWithMeeting(status, page = 0, size = pageSize)
            result.onSuccess { page ->
                _state.value = NotificationsUiState(
                    items = page.content,
                    page = page.number,
                    isLast = page.last,
                    isLoading = false
                )
            }.onFailure {
                _state.value = _state.value.copy(isLoading = false, error = it.message)
            }
        }
    }

    fun loadNextPage() {
        val s = _state.value
        if (s.isLast || s.isLoading || s.isLoadingMore) return

        _state.value = s.copy(isLoadingMore = true, error = null)

        viewModelScope.launch {
            val next = s.page + 1
            val result = repo.getMyInvitationsWithMeeting(status, page = next, size = pageSize)

            result.onSuccess { page ->
                _state.value = s.copy(
                    items = s.items + page.content,
                    page = page.number,
                    isLast = page.last,
                    isLoadingMore = false
                )
            }.onFailure {
                _state.value = s.copy(isLoadingMore = false, error = it.message)
            }
        }
    }

    fun respond(invitationId: Long, newStatus: String) {
        viewModelScope.launch {
            repo.respondInvitation(invitationId, newStatus)
                .onSuccess {
                    _state.value = _state.value.copy(
                        items = _state.value.items.filterNot { it.id == invitationId }
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(error = it.message)
                }
        }
    }
}
