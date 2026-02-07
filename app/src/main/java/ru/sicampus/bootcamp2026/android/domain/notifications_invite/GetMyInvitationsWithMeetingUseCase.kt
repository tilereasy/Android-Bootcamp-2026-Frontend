package ru.sicampus.bootcamp2026.android.domain.notifications_invite

import ru.sicampus.bootcamp2026.android.data.InvitationsRepository

class GetMyInvitationsWithMeetingUseCase(
    private val repo: InvitationsRepository
) {
    suspend operator fun invoke(status: String, page: Int, size: Int) =
        repo.getMyInvitationsWithMeeting(status, page, size)
}
