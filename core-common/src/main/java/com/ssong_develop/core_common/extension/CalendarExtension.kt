package com.ssong_develop.core_common.extension

import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.DAY_OF_WEEK
import java.util.Calendar.LONG
import java.util.Calendar.MONTH
import java.util.Calendar.SATURDAY
import java.util.Calendar.SUNDAY
import java.util.Calendar.YEAR
import java.util.Date
import java.util.Locale

fun Calendar.withTime(date: Date) {
    clear()
    time = date
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

fun Calendar.isSunday(): Boolean = get(DAY_OF_WEEK) == SUNDAY

fun Calendar.isSaturday(): Boolean = get(DAY_OF_WEEK) == SATURDAY

fun Calendar.toPrettyMonthString(
    style: Int = LONG,
    locale: Locale = Locale.KOREA
): String {
    val month = getDisplayName(MONTH, style, locale)
    val year = get(YEAR).toString()
    check(month != null) { throw IllegalStateException("month value is wrong") }
    return if (locale.country.equals(Locale.KOREA.country)) {
        "${year}년 $month"
    } else {
        "$year $month"
    }
}

fun Calendar.toPrettyDateString(): String {
    val month = (get(MONTH) + 1).toString()
    val year = get(YEAR).toString()
    val day = get(DAY_OF_MONTH).toString()
    return "$year.$month.$day"
}