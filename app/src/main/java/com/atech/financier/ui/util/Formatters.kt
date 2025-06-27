package com.atech.financier.ui.util

/** Extension-функция для форматирования денежной суммы */
fun String.asNumber() : String {
    var number = this
    val tail = if (number.endsWith("00")) "" else ",${number.takeLast(2)}"
    var sign = ""
    if (number.startsWith("-")) {
        sign = "-"
        number = number.drop(1)
    }
    return sign + number.dropLast(3).reversed().chunked(3).joinToString(" ").reversed() + tail
}
