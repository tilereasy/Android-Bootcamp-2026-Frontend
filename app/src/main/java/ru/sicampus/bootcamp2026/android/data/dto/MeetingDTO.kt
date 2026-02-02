package ru.sicampus.bootcamp2026.android.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeetingDTO(
    val id: Long,
    val organizerId: Long,
    val title: String,
    val description: String,
    val startAt: String,
    val endAt: String,
    val createdAt: String? = null
)