package com.ssong_develop.core_common.extension

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun Date.isTheSameDay(comparedDate: Date): Boolean {
    val calendar = Calendar.getInstance()
    calendar.withTime(this)
    val comparedCalendarDate = Calendar.getInstance()
    comparedCalendarDate.withTime(comparedDate)
    return calendar.get(Calendar.DAY_OF_YEAR) == comparedCalendarDate.get(Calendar.DAY_OF_YEAR) &&
            calendar.get(Calendar.YEAR) == comparedCalendarDate.get(Calendar.YEAR) &&
            calendar.get(Calendar.MONTH) == comparedCalendarDate.get(Calendar.MONTH) &&
            calendar.get(Calendar.DAY_OF_MONTH) == comparedCalendarDate.get(Calendar.DAY_OF_MONTH)
}

fun Date.isToday(): Boolean {
    val calendar = Calendar.getInstance()
    calendar.withTime(this)
    val todayCalendar = Calendar.getInstance()
    return (todayCalendar.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR) &&
            todayCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))
}

fun Date.convertDateToString(): String? {
    val dateFormat = SimpleDateFormat("yyyy.MM.dd")
    return runCatching {
        dateFormat.format(this)
    }.getOrNull()
}
