package com.ssong_develop.core_designsystem.calendar.listener

import com.ssong_develop.core_designsystem.calendar.model.CalendarDay

fun interface CalendarDayClickListener {
    fun onClickCalendarDay(calendarDay: CalendarDay)
}