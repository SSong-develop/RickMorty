package com.ssong_develop.core_designsystem.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ssong_develop.core_common.extension.isWeekend
import com.ssong_develop.core_common.extension.toPrettyDateString
import com.ssong_develop.core_designsystem.NoRippleRecyclerView
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
import java.util.Calendar.YEAR
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class RickMortyCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val timeZone = TimeZone.getDefault()
    private val locale = Locale.KOREA
    private val calendarAdapter = CalendarAdapter(
        onDayClick = { date ->
            selectDay = date
        }
    )
    private val calendar = Calendar.getInstance(timeZone, locale)
    var selectDay: Date? = null

    private var onClickBeforeMonthListener: OnClickBeforeMonthListener? = null
    private var onClickNextMonthListener: OnClickNextMonthListener? = null

    private val descriptionView = ViewCalendarWeekDescriptionBinding.inflate(
        LayoutInflater.from(context), this, false
    )

    private val calendarView = NoRippleRecyclerView(context).apply {
        id = ViewCompat.generateViewId()
        adapter = calendarAdapter
        layoutManager = GridLayoutManager(context, 7).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = 1
            }
        }
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        setHasFixedSize(true)
    }

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = LinearLayout.VERTICAL

        addView(descriptionView.root)
        addView(calendarView)
        calendarAdapter.submitList(buildCalendarData())
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {
        /** no - op **/
    }

    // 리팩토링
    private fun buildCalendarData(): List<CalendarDay> {
        val proxyCalendar = Calendar.getInstance().apply {
            this.set(MONTH, calendar.get(MONTH))
            this.set(DAY_OF_MONTH, calendar.get(DAY_OF_MONTH))
            this.set(YEAR, calendar.get(YEAR))
        }

        val totalDayInMonth = calendar.getActualMaximum(DAY_OF_MONTH)
        val calendarDayList = mutableListOf<CalendarDay>()
        (1..totalDayInMonth).forEach { day ->
            proxyCalendar.set(DAY_OF_MONTH, day)
            val dayOfWeek = proxyCalendar.get(DAY_OF_WEEK)
            val dateType = if (proxyCalendar.isWeekend()) {
                DateType.WEEKEND
            } else {
                DateType.DAY
            }
            when (day) {
                1 -> {
                    // 비어있는 날짜 채운 후에 하나 채워야 함
                    calendarDayList.addAll(createStartEmptyView(dayOfWeek, proxyCalendar))
                    calendarDayList.add(
                        CalendarDay.Day(
                            label = day.toString(),
                            prettyLabel = proxyCalendar.toPrettyDateString(),
                            date = proxyCalendar.time,
                            dateType = dateType
                        )
                    )
                }
                totalDayInMonth -> {
                    calendarDayList.add(
                        CalendarDay.Day(
                            label = day.toString(),
                            prettyLabel = proxyCalendar.toPrettyDateString(),
                            date = proxyCalendar.time,
                            dateType = dateType
                        )
                    )
                    calendarDayList.addAll(createEndEmptyView(dayOfWeek, calendar))
                }
                else -> {
                    calendarDayList.add(
                        CalendarDay.Day(
                            label = day.toString(),
                            prettyLabel = proxyCalendar.toPrettyDateString(),
                            date = proxyCalendar.time,
                            dateType = dateType
                        )
                    )
                }
            }
        }

        return calendarDayList
    }

    private fun createStartEmptyView(
        dayOfWeek: Int,
        calendar: Calendar
    ): List<CalendarDay.Empty> {
        val previousCalendar = Calendar.getInstance().apply {
            set(MONTH, calendar.get(MONTH))
            set(DAY_OF_MONTH, calendar.get(DAY_OF_MONTH))
            set(YEAR, calendar.get(YEAR))
        }.also {
            it.add(MONTH, -1)
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
            emptyDayList.add(CalendarDay.Empty(startDayInPreviousMonth++.toString()))
        }
        return emptyDayList
    }

    private fun createEndEmptyView(
        dayOfWeek: Int,
        calendar: Calendar
    ): List<CalendarDay.Empty> {
        val nextCalendar = Calendar.getInstance().apply {
            set(MONTH, calendar.get(MONTH))
            set(DAY_OF_MONTH, calendar.get(DAY_OF_MONTH))
            set(YEAR, calendar.get(YEAR))
        }.also {
            it.add(MONTH, -1)
        }
        var totalDayInMonth = nextCalendar.getActualMaximum(DAY_OF_MONTH)
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
            emptyDayList.add(
                CalendarDay.Empty(totalDayInMonth++.toString())
            )
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