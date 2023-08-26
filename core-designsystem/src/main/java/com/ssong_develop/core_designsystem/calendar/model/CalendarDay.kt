package com.ssong_develop.core_designsystem.calendar.model

import java.util.Date

/**
 *
 * @param calendarType
 */
internal sealed class CalendarDay(val type: CalendarDayType) {
    /**
     *
     * @param label
     * @param prettyLabel
     * @param date
     * @param state
     */
    internal data class Day(
        val label: String,
        val prettyLabel: String,
        val date: Date,
        val dateType: DateType = DateType.DAY
    ): CalendarDay(CalendarDayType.DAY)

    /**
     *
     * @param label
     */
    internal data class Empty(
        val label: String
    ): CalendarDay(CalendarDayType.EMPTY)
}