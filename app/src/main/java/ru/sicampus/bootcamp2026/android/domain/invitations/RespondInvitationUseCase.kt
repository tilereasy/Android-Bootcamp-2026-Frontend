package ru.sicampus.bootcamp2026.android.domain.invitations

import ru.sicampus.bootcamp2026.android.data.InvitationsRepository

class RespondInvitationUseCase(
    private val repo: InvitationsRepository
) {
    suspend operator fun invoke(id: Long, status: String) =
        repo.respondInvitation(id, status)
}
