package ru.sicampus.bootcamp2026.android.data.dto

import kotlinx.serialization.Serializable

@Serializable
enum class InvitationStatusDto { PENDING, ACCEPTED, DECLINED }

@Serializable
data class InvitationDTO(
    val id: Long,
    val meetingId: Long,
    val inviteeId: Long,
    val status: InvitationStatusDto,
    val respondedAt: String? = null,
    val createdAt: String
)