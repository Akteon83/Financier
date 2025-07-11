package com.atech.financier.ui.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

/** Extension-функции для форматирования отображаемых данных */

fun Long.toAmount(): String = "${if (this.sign == -1) "- " else ""}${
    (if (abs(this) > 99) {
    abs(this).toString()
        .dropLast(2)
        .reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()
    } else "0")
},${("0" + abs(this).toString()).takeLast(2)}"

fun String.toBalance(): Long {
    if (this.isBlank()) return 0
    val coefficient = if (this.trim().firstOrNull() == '-') -1 else 1
    if (!this.contains(',') && !this.contains('.')) {
        return this.filter { it.isDigit() }.toLong() * 100 * coefficient
    }
    forEachIndexed { i, it ->
        if (it == ',' || it == '.') {
            return (this.take(i).filter { it.isDigit() } + "${this.drop(i + 1)
                .filter { it.isDigit() }}00".take(2)).toLong() * coefficient
        }
    }
    return 0L
}

fun Instant.toDateTime(): String = DateTimeFormatter
    .ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
    .withLocale(Locale.getDefault())
    .withZone(ZoneId.systemDefault())
    .format(this)

fun LocalDate.toFormattedDate(): String = DateTimeFormatter
    .ofLocalizedDate(FormatStyle.SHORT)
    .withLocale(Locale.getDefault())
    .withZone(ZoneId.systemDefault())
    .format(this)

fun LocalTime.toFormattedTime(): String = DateTimeFormatter
    .ofLocalizedTime(FormatStyle.SHORT)
    .withLocale(Locale.getDefault())
    .withZone(ZoneId.systemDefault())
    .format(this)

fun String.toCurrencySymbol(): String = when (this) {
    "RUB" -> "₽"
    "USD" -> "$"
    "EUR" -> "€"
    else -> "#"
}

fun String.toCurrencyString(): String = when (this) {
    "₽" -> "RUB"
    "$" -> "USD"
    "€" -> "EUR"
    else -> "#"
}
