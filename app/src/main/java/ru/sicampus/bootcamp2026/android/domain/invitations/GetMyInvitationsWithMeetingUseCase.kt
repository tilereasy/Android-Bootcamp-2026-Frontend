package ru.sicampus.bootcamp2026.android.domain.invitations

import ru.sicampus.bootcamp2026.android.data.InvitationsRepository

class GetMyInvitationsWithMeetingUseCase(
    private val repo: InvitationsRepository
) {
    suspend operator fun invoke(status: String, page: Int, size: Int) =
        repo.getMyInvitationsWithMeeting(status, page, size)
}
