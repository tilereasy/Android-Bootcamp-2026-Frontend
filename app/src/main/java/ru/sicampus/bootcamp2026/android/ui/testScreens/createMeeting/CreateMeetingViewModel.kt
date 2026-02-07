package ru.sicampus.bootcamp2026.android.ui.testScreens.createMeeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2026.android.data.CreateMeetingRepository
import ru.sicampus.bootcamp2026.android.data.source.AuthLocalDataSource
import ru.sicampus.bootcamp2026.android.data.source.InvitationNetworkDataSource
import ru.sicampus.bootcamp2026.android.data.source.MeetingsNetworkDataSource
import ru.sicampus.bootcamp2026.android.data.source.PersonsNetworkDataSource
import ru.sicampus.bootcamp2026.android.domain.invitation.CreateMeetingWithInvitationsUseCase
import ru.sicampus.bootcamp2026.android.domain.invitation.SearchPersonByEmailUseCase
import java.time.*
import java.time.format.DateTimeFormatter

class CreateMeetingViewModel : ViewModel() {

    private val repository by lazy {
        CreateMeetingRepository(
            meetingsNetworkDataSource = MeetingsNetworkDataSource(),
            invitationNetworkDataSource = InvitationNetworkDataSource(),
            personNetworkDataSource = PersonsNetworkDataSource(),
            authLocalDataSource = AuthLocalDataSource
        )
    }

    private val searchPersonByEmailUseCase by lazy {
        SearchPersonByEmailUseCase(repository)
    }

    private val createMeetingWithInvitationsUseCase by lazy {
        CreateMeetingWithInvitationsUseCase(repository)
    }

    private val _uiState = MutableStateFlow<CreateMeetingState>(
        CreateMeetingState.Data()
    )
    val uiState: StateFlow<CreateMeetingState> = _uiState.asStateFlow()

    private val _actionFlow = MutableSharedFlow<CreateMeetingAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    fun onIntent(intent: CreateMeetingIntent) {
        when (intent) {
            is CreateMeetingIntent.UpdateTitle -> {
                updateStateIfData { it.copy(title = intent.title, error = null) }
                validateForm()
            }

            is CreateMeetingIntent.UpdateDescription -> {
                updateStateIfData { it.copy(description = intent.description, error = null) }
                validateForm()
            }

            is CreateMeetingIntent.UpdateDate -> {
                updateStateIfData { it.copy(date = intent.date, error = null) }
                validateForm()
            }

            is CreateMeetingIntent.UpdateStartHour -> {
                updateStateIfData { it.copy(startHour = intent.hour, error = null) }
                validateForm()
            }

            is CreateMeetingIntent.UpdateEndHour -> {
                updateStateIfData { it.copy(endHour = intent.hour, error = null) }
                validateForm()
            }

            is CreateMeetingIntent.UpdateSearchQuery -> {
                updateStateIfData {
                    it.copy(searchQuery = intent.query, searchResults = emptyList())
                }
            }

            is CreateMeetingIntent.SearchPerson -> searchPerson()

            is CreateMeetingIntent.AddParticipant -> {
                updateStateIfData { old ->
                    old.copy(
                        participants = old.participants + intent.participant,
                        searchQuery = "",
                        searchResults = emptyList(),
                        error = null
                    )
                }
                validateForm()
            }

            is CreateMeetingIntent.RemoveParticipant -> {
                updateStateIfData { old ->
                    old.copy(
                        participants = old.participants.filter { it.id != intent.participantId },
                        error = null
                    )
                }
                validateForm()
            }

            is CreateMeetingIntent.CreateMeeting -> createMeeting()
        }
    }

    private fun searchPerson() {
        val state = (_uiState.value as? CreateMeetingState.Data) ?: return
        val query = state.searchQuery.trim()
        if (query.isBlank()) return

        updateStateIfData { it.copy(isSearching = true) }

        viewModelScope.launch {
            searchPersonByEmailUseCase.invoke(query).fold(
                onSuccess = { person ->
                    val alreadyAdded = state.participants.any { it.id == person.id }
                    updateStateIfData {
                        it.copy(
                            isSearching = false,
                            searchResults = if (alreadyAdded) emptyList() else listOf(person),
                            error = if (alreadyAdded) "Этот участник уже добавлен" else null
                        )
                    }
                },
                onFailure = {
                    updateStateIfData {
                        it.copy(isSearching = false, searchResults = emptyList())
                    }
                }
            )
        }
    }

    private fun validateForm() {
        updateStateIfData {
            val hint = getValidationHint(it)
            it.copy(
                isEnabledCreate = hint == null,
                validationHint = hint
            )
        }
    }

    private fun createMeeting() {
        val state = (_uiState.value as? CreateMeetingState.Data) ?: return

        val validationHint = getValidationHint(state)
        if (validationHint != null) {
            updateStateIfData { it.copy(error = validationHint) }
            return
        }

        _uiState.value = CreateMeetingState.Loading

        viewModelScope.launch {
            try {
                val startAt = convertToIsoUtc(state.date, state.startHour)
                val endAt = convertToIsoUtc(state.date, state.endHour)

                createMeetingWithInvitationsUseCase.invoke(
                    title = state.title.trim(),
                    description = state.description.trim(),
                    startAt = startAt,
                    endAt = endAt,
                    inviteeIds = state.participants.map { it.id }
                ).fold(
                    onSuccess = {
                        _actionFlow.emit(CreateMeetingAction.MeetingCreated)
                    },
                    onFailure = {
                        _uiState.value = state.copy(
                            error = it.message ?: "Ошибка создания встречи"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = state.copy(
                    error = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    /**
     * ЛОКАЛЬНОЕ время пользователя → UTC ISO-8601
     */
    private fun convertToIsoUtc(date: String, hour: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        val localDate = LocalDate.parse(date, dateFormatter)
        val localTime = LocalTime.of(hour.toInt(), 0)
        val localDateTime = LocalDateTime.of(localDate, localTime)

        return localDateTime
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT)
    }

    private fun updateStateIfData(
        block: (CreateMeetingState.Data) -> CreateMeetingState
    ) {
        _uiState.update { state ->
            (state as? CreateMeetingState.Data)?.let(block) ?: state
        }
    }

    private fun getValidationHint(state: CreateMeetingState.Data): String? {
        return when {
            state.title.isBlank() -> "Введите название встречи"
            state.description.isBlank() -> "Введите описание"
            state.date.isBlank() -> "Выберите дату"
            state.startHour.isBlank() -> "Выберите время начала"
            state.endHour.isBlank() -> "Выберите время окончания"
            state.endHour.toInt() <= state.startHour.toInt() ->
                "Время окончания должно быть больше времени начала"
            state.participants.isEmpty() -> "Добавьте хотя бы одного участника"
            else -> null
        }
    }
}
