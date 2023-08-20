package com.ssong_develop.core_common.extension

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

fun String.dayNameParseToKorea(): String = when (this) {
    "SUNDAY" -> "일"
    "MONDAY" -> "월"
    "TUESDAY" -> "화"
    "WEDNESDAY" -> "수"
    "THURSDAY" -> "목"
    "FRIDAY" -> "금"
    "SATURDAY" -> "토"
    else -> throw IllegalStateException("This is not day String")
}

fun String.convertStringToDate(): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd")
    return runCatching {
        format.parse(this)
    }.getOrNull()
}

fun String.convertToLocalDate(): LocalDate? {
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return runCatching {
        LocalDate.parse(this, format)
    }.getOrNull()
}

// yyyy-MM-dd -> yyyy.MM.dd
fun String.convertDateStringToPrettyDateString(): String {
    return this.replace('-', '.')
}

fun String.convertDateStringToInt(): Int {
    return this.filter { char -> char != '.' }.toInt()
}

fun String.filterAndConvertToInt(): Int {
    return this.filter { char -> char != '-' }.toInt()
}