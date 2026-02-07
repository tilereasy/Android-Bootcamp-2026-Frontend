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
import java.time.LocalDate
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

    private val searchPersonByEmailUseCase by lazy { SearchPersonByEmailUseCase(repository) }
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
                updateStateIfData { it.copy(searchQuery = intent.query, searchResults = emptyList()) }
            }

            is CreateMeetingIntent.SearchPerson -> {
                searchPerson()
            }

            is CreateMeetingIntent.AddParticipant -> {
                updateStateIfData { oldState ->
                    val updatedParticipants = oldState.participants + intent.participant
                    oldState.copy(
                        participants = updatedParticipants,
                        searchQuery = "",
                        searchResults = emptyList(),
                        error = null
                    )
                }
                validateForm()
            }

            is CreateMeetingIntent.RemoveParticipant -> {
                updateStateIfData { oldState ->
                    oldState.copy(
                        participants = oldState.participants.filter { it.id != intent.participantId },
                        error = null
                    )
                }
                validateForm()
            }

            is CreateMeetingIntent.CreateMeeting -> {
                createMeeting()
            }
        }
    }

    private fun searchPerson() {
        val currentState = (_uiState.value as? CreateMeetingState.Data) ?: return
        val query = currentState.searchQuery.trim()

        if (query.isBlank()) {
            updateStateIfData { it.copy(searchResults = emptyList()) }
            return
        }

        updateStateIfData { it.copy(isSearching = true) }

        viewModelScope.launch {
            searchPersonByEmailUseCase.invoke(query).fold(
                onSuccess = { personResponse ->
                    val isAlreadyAdded = currentState.participants.any { it.id == personResponse.id }
                    if (isAlreadyAdded) {
                        updateStateIfData { oldState ->
                            oldState.copy(
                                isSearching = false,
                                searchResults = emptyList(),
                                error = "Этот участник уже добавлен"
                            )
                        }
                    } else {
                        updateStateIfData { oldState ->
                            oldState.copy(
                                isSearching = false,
                                searchResults = listOf(personResponse)
                            )
                        }
                    }
                },
                onFailure = {
                    updateStateIfData { oldState ->
                        oldState.copy(
                            isSearching = false,
                            searchResults = emptyList()
                        )
                    }
                }
            )
        }
    }

    private fun validateForm() {
        updateStateIfData { state ->
            val hint = getValidationHint(state)
            state.copy(
                isEnabledCreate = hint == null,
                validationHint = hint
            )
        }
    }

    private fun getValidationHint(state: CreateMeetingState.Data): String? {
        return when {
            state.title.trim().isBlank() -> "Введите название встречи"
            state.description.trim().isBlank() -> "Введите описание"
            state.date.isBlank() -> "Выберите дату"
            !isValidDate(state.date) -> "Некорректный формат даты (дд.мм.гггг)"
            state.startHour.isBlank() -> "Выберите время начала"
            !isValidHour(state.startHour) -> "Некорректный час (от 0 до 23)"
            state.endHour.isBlank() -> "Выберите время окончания"
            !isValidHour(state.endHour) -> "Некорректный час (от 0 до 23)"
            !isValidTimeRange(state.startHour, state.endHour) -> "Время окончания должно быть больше времени начала"
            state.participants.isEmpty() -> "Добавьте хотя бы одного участника"
            else -> null
        }
    }

    private fun isValidDate(date: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            LocalDate.parse(date, formatter)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidHour(hour: String): Boolean {
        return try {
            val h = hour.toIntOrNull() ?: return false
            h in 0..23
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidTimeRange(startHour: String, endHour: String): Boolean {
        return try {
            val start = startHour.toIntOrNull() ?: return false
            val end = endHour.toIntOrNull() ?: return false
            end > start
        } catch (e: Exception) {
            false
        }
    }

    private fun createMeeting() {
        val state = (_uiState.value as? CreateMeetingState.Data) ?: return

        // КРИТИЧНО: Сохраняем текущее состояние перед валидацией
        val currentState = state

        // Проверка валидности перед отправкой
        val validationHint = getValidationHint(state)
        if (validationHint != null) {
            updateStateIfData { it.copy(error = validationHint) }
            return
        }

        _uiState.value = CreateMeetingState.Loading

        viewModelScope.launch {
            try {
                // Преобразуем дату и время в ISO 8601 формат
                val startAt = convertToISO8601(currentState.date, currentState.startHour)
                val endAt = convertToISO8601(currentState.date, currentState.endHour)

                val inviteeIds = currentState.participants.map { it.id }

                createMeetingWithInvitationsUseCase.invoke(
                    title = currentState.title.trim(),
                    description = currentState.description.trim(),
                    startAt = startAt,
                    endAt = endAt,
                    inviteeIds = inviteeIds
                ).fold(
                    onSuccess = {
                        _actionFlow.emit(CreateMeetingAction.MeetingCreated)
                    },
                    onFailure = { error ->
                        // КРИТИЧНО: Возвращаемся в Data state с ошибкой
                        _uiState.value = currentState.copy(
                            error = error.message ?: "Ошибка создания встречи"
                        )
                    }
                )
            } catch (e: Exception) {
                // КРИТИЧНО: Ловим любые исключения и возвращаемся в Data state
                _uiState.value = currentState.copy(
                    error = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    private fun convertToISO8601(date: String, hour: String): String {
        // date: "07.02.2026", hour: "09"
        // result: "2026-02-07T09:00:00Z"
        try {
            val dateParts = date.split(".")
            val day = dateParts[0].padStart(2, '0')
            val month = dateParts[1].padStart(2, '0')
            val year = dateParts[2]
            val hourPadded = hour.padStart(2, '0')

            return "$year-$month-${day}T$hourPadded:00:00Z"
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid date/time format: date=$date, hour=$hour")
        }
    }

    private fun updateStateIfData(lambda: (CreateMeetingState.Data) -> CreateMeetingState) {
        _uiState.update { state ->
            (state as? CreateMeetingState.Data)?.let { lambda.invoke(it) } ?: state
        }
    }
}