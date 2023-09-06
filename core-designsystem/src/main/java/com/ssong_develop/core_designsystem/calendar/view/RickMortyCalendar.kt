package com.ssong_develop.core_designsystem.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import com.ssong_develop.core_common.extension.isWeekend
import com.ssong_develop.core_common.extension.toPrettyDateString
import com.ssong_develop.core_designsystem.calendar.listener.OnClickBeforeMonthListener
import com.ssong_develop.core_designsystem.calendar.listener.OnClickNextMonthListener
import com.ssong_develop.core_designsystem.calendar.model.CalendarDay
import com.ssong_develop.core_designsystem.calendar.model.DateType
import com.ssong_develop.core_designsystem.databinding.ViewCalendarWeekDescriptionBinding
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.DAY_OF_WEEK
import java.util.Calendar.FRIDAY
import java.util.Calendar.MONDAY
import java.util.Calendar.MONTH
import java.util.Calendar.SATURDAY
import java.util.Calendar.SUNDAY
import java.util.Calendar.THURSDAY
import java.util.Calendar.TUESDAY
import java.util.Calendar.WEDNESDAY
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class RickMortyCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    /** calendar instance **/
    private val timeZone = TimeZone.getDefault()
    private val locale = Locale.KOREA
    private val calendar = Calendar.getInstance(timeZone, locale)

    /** rickMorty calendar instance **/
    var selectDay: Date? = null
    private val calendarDayAdapter = CalendarDayAdapter { date -> selectDay = date }

    /** Views **/
    private val descriptionView = ViewCalendarWeekDescriptionBinding.inflate(
        LayoutInflater.from(context), this, false
    )

    private val calendarDayView = CalendarDayRecyclerView(context).apply {
        id = ViewCompat.generateViewId()
        adapter = calendarDayAdapter
    }

    /** Listener **/
    private var onClickBeforeMonthListener: OnClickBeforeMonthListener? = null
    private var onClickNextMonthListener: OnClickNextMonthListener? = null

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = LinearLayout.VERTICAL

        addView(descriptionView.root)
        addView(calendarDayView)
        calendarDayAdapter.submitList(getCalendarDayList())
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {
        /** no - op **/
    }

    private fun getCalendarDayList(): List<CalendarDay> {
        val dayOfMonthTargetCalendar = Calendar.getInstance(timeZone, locale).apply {
            this.set(DAY_OF_MONTH, calendar.get(DAY_OF_MONTH))
        }

        val totalDayInMonth = dayOfMonthTargetCalendar.getActualMaximum(DAY_OF_MONTH)
        val calendarDayList = mutableListOf<CalendarDay>()
        (1..totalDayInMonth).forEach { day ->
            dayOfMonthTargetCalendar.set(DAY_OF_MONTH, day)
            val dayOfWeek = dayOfMonthTargetCalendar.get(DAY_OF_WEEK)
            val dateType = if (dayOfMonthTargetCalendar.isWeekend()) {
                DateType.WEEKEND
            } else {
                DateType.DAY
            }

            when (day) {
                1 -> {
                    calendarDayList.addAll(createStartEmptyView(dayOfWeek))
                    calendarDayList.add(
                        CalendarDay.Day(
                            label = day.toString(),
                            prettyLabel = dayOfMonthTargetCalendar.toPrettyDateString(),
                            date = dayOfMonthTargetCalendar.time,
                            dateType = dateType
                        )
                    )
                }

                totalDayInMonth -> {
                    calendarDayList.add(
                        CalendarDay.Day(
                            label = day.toString(),
                            prettyLabel = dayOfMonthTargetCalendar.toPrettyDateString(),
                            date = dayOfMonthTargetCalendar.time,
                            dateType = dateType
                        )
                    )
                    calendarDayList.addAll(createEndEmptyView(dayOfWeek))
                }

                else -> {
                    calendarDayList.add(
                        CalendarDay.Day(
                            label = day.toString(),
                            prettyLabel = dayOfMonthTargetCalendar.toPrettyDateString(),
                            date = dayOfMonthTargetCalendar.time,
                            dateType = dateType
                        )
                    )
                }
            }
        }

        return calendarDayList
    }

    /**
     * to create current month calendar first week, create previous month day(Calendar.Empty)
     *
     * if current month calendar first week contains previous month day (ex. 2023.09.01 is ThurDay then 2023.08's last week Sun, Mon, Tues, Wedn need)
     */
    private fun createStartEmptyView(dayOfWeek: Int): List<CalendarDay.Empty> {
        val previousCalendar = Calendar.getInstance(timeZone, locale).apply {
            add(MONTH, -1)
        }

        val numberOfEmptyView = when (dayOfWeek) {
            MONDAY -> 1
            TUESDAY -> 2
            WEDNESDAY -> 3
            THURSDAY -> 4
            FRIDAY -> 5
            SATURDAY -> 6
            else -> 0
        }
        var startDayInPreviousMonth =
            previousCalendar.getActualMaximum(DAY_OF_MONTH) - numberOfEmptyView + 1

        val emptyDayList = mutableListOf<CalendarDay.Empty>()
        repeat((0 until numberOfEmptyView).count()) {
            emptyDayList.add(CalendarDay.Empty(label = startDayInPreviousMonth++.toString()))
        }
        return emptyDayList
    }

    /**
     * to create current month calendar last week, create next month day(Calendar.Empty)
     *
     * if current month calendar last week contains next month day (ex. 2023.09.30 is WednesDay then 2023.10's first week Thur, Fri, Sat need)
     */
    private fun createEndEmptyView(dayOfWeek: Int): List<CalendarDay.Empty> {
        var day = 1
        val numberOfEmptyView = when (dayOfWeek) {
            SUNDAY -> 6
            MONDAY -> 5
            TUESDAY -> 4
            WEDNESDAY -> 3
            THURSDAY -> 2
            FRIDAY -> 1
            else -> 0
        }

        val emptyDayList = mutableListOf<CalendarDay.Empty>()
        repeat((0 until numberOfEmptyView).count()) {
            emptyDayList.add(CalendarDay.Empty(label = (day++).toString()))
        }
        return emptyDayList
    }

    fun setOnBeforeMonthClickListener(listener: OnClickBeforeMonthListener) {
        this.onClickBeforeMonthListener = listener
    }

    fun setOnBeforeMonthClickListener(block: () -> Unit) {
        this.onClickBeforeMonthListener = OnClickBeforeMonthListener(block)
    }

    fun setOnNextMonthClickListener(listener: OnClickNextMonthListener) {
        this.onClickNextMonthListener = listener
    }

    fun setOnNextMonthClickListener(block: () -> Unit) {
        this.onClickNextMonthListener = OnClickNextMonthListener(block)
    }
}