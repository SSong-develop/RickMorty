package com.ssong_develop.core_designsystem.calendar.listener

import java.util.Date

fun interface OnClickCalendarDayListener {
    fun onClick(date: Date)
}