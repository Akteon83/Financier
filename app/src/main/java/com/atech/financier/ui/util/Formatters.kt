package com.atech.financier.ui.util

fun String.asNumber() : String {
    var number = this
    if (number.endsWith("00")) number = number.dropLast(3)
    return number.reversed().chunked(3).joinToString(" ").reversed()
}
