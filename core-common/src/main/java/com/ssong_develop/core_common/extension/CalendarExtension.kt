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

// 현재 날짜로 부터 날이 이미 지났는지 체크하는 함수
fun Calendar.isBefore(otherCalendar: Calendar): Boolean {
    return get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR)
            && get(Calendar.MONTH) == otherCalendar.get(Calendar.MONTH)
            && get(Calendar.DAY_OF_MONTH) < otherCalendar.get(Calendar.DAY_OF_MONTH)
}

fun Calendar.isBeforeCalendar(otherCalendar: Calendar): Boolean {
    if (get(Calendar.YEAR) < otherCalendar.get(Calendar.YEAR)) return true
    return if (get(Calendar.MONTH) < otherCalendar.get(Calendar.MONTH)) {
        true
    } else if (get(Calendar.MONTH) == otherCalendar.get(Calendar.MONTH)) {
        get(Calendar.DAY_OF_MONTH) <= otherCalendar.get(Calendar.DAY_OF_MONTH)
    } else {
        false
    }
}

// 현재 날짜로 부터 이후의 날인지 체크하는 함수
fun Calendar.isAfter(otherCalendar: Calendar): Boolean {
    return get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR)
            && get(Calendar.MONTH) == otherCalendar.get(Calendar.MONTH)
            && get(Calendar.DAY_OF_MONTH) > otherCalendar.get(Calendar.DAY_OF_MONTH)
}

// 주말을 알려주는 함수
fun Calendar.isWeekend(): Boolean {
    return get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
}

// 총 달수의 차이
fun Calendar.totalMonthDifference(startCalendar: Calendar): Int {
    val yearDiff = get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)
    val monthDiff = get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)

    return monthDiff + (yearDiff * 12)
}
