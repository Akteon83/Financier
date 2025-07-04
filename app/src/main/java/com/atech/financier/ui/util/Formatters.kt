package com.atech.financier.ui.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

/** Extension-функции для форматирования отображаемых данных */

fun Long.toAmount(): String = "${if (this.sign == -1) "- " else ""}${
    abs(this).toString().dropLast(2).reversed().chunked(3).joinToString(" ").reversed()
},${this.toString().takeLast(2)}"

fun String.toBalance(): Long {
    var coefficient = 1
    if (this.firstOrNull() == '-') {
        coefficient = -1
        this.drop(1)
    }
    if (this.filter { it.isDigit() } == this) return this.toLong()
    if (!this.contains(',') && !this.contains('.')) return this.filter { it.isDigit() }.toLong()
    forEachIndexed { i, it ->
        if (it == ',' || it == '.') return (this.take(i).filter { it.isDigit() }
                + "${this.drop(i + 1).filter { it.isDigit() }}00".take(2)).toLong() * coefficient
    }
    return 0L
}

fun Instant.toDateTime(): String = DateTimeFormatter
    .ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
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
