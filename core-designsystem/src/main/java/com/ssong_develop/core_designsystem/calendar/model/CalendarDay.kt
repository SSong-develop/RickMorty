package com.ssong_develop.core_designsystem.calendar.model

import java.util.Date

/**
 *
 * @param calendarType
 */
sealed class CalendarDay(
    val calendarType: CalendarType
) {
    /**
     *
     */
    object Week: CalendarDay(CalendarType.WEEK)

    /**
     *
     * @param label
     * @param prettyLabel
     * @param date
     * @param state
     */
    data class Day(
        val label: String,
        val prettyLabel: String,
        val date: Date,
        val state: DateType = DateType.WEEKDAY
    ): CalendarDay(CalendarType.DAY)

    /**
     *
     * @param label
     */
    data class Empty(
        val label: String
    ): CalendarDay(CalendarType.EMPTY)
}