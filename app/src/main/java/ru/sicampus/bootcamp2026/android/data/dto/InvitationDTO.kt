package ru.sicampus.bootcamp2026.android.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class InvitationDTO(
    val id: Long,
    val meetingId: Long,
    val inviteeId: Long,
    val status: String, // "PENDING", "ACCEPTED", "DECLINED"
    val respondedAt: String? = null,
    val createdAt: String? = null
)