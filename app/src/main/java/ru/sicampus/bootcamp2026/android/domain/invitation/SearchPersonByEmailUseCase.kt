package ru.sicampus.bootcamp2026.android.domain.invitation

import ru.sicampus.bootcamp2026.android.data.CreateMeetingRepository
import ru.sicampus.bootcamp2026.android.data.source.PersonResponse

class SearchPersonByEmailUseCase(
    private val repository: CreateMeetingRepository
) {
    suspend operator fun invoke(email: String): Result<PersonResponse> {
        return repository.searchPersonByEmail(email)
    }
}