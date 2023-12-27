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

fun String.parseToYYYYmmDD(): String {
    val (month, day, year) = this.split(' ')
    val monthNumberString = when (month) {
        "January" -> "01"
        "February" -> "02"
        "March" -> "03"
        "April" -> "04"
        "May" -> "05"
        "June" -> "06"
        "July" -> "07"
        "August" -> "08"
        "September" -> "09"
        "October" -> "10"
        "November" -> "11"
        "December" -> "12"
        else -> throw java.lang.IllegalStateException("not valid")
    }
    return "${year}-${monthNumberString}-${day.dropLast(1)}"
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