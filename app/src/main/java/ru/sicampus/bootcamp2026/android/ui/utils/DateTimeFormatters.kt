package ru.sicampus.bootcamp2026.android.ui.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val RU = Locale("ru")

// !!!!! Фиксированный часовой пояс: Москва
private val MOSCOW_ZONE: ZoneId = ZoneId.of("Europe/Moscow")

private val DATE_FORMAT: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yyyy", RU)

private val TIME_FORMAT: DateTimeFormatter =
    DateTimeFormatter.ofPattern("HH:mm", RU)

private fun parseIsoToZdtMoscow(iso: String): ZonedDateTime {
    // Поддерживает "2026-02-04T20:00:14.167Z" (UTC)
    val instant = Instant.parse(iso)
    return instant.atZone(MOSCOW_ZONE)
}

fun formatDateDdMmYyyy(iso: String): String =
    DATE_FORMAT.format(parseIsoToZdtMoscow(iso))

fun formatTimeHm(iso: String): String =
    TIME_FORMAT.format(parseIsoToZdtMoscow(iso))

fun formatTimeRange(startIso: String, endIso: String): String =
    "${formatTimeHm(startIso)} – ${formatTimeHm(endIso)}"
