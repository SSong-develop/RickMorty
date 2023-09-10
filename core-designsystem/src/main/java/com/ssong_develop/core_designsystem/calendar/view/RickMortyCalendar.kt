package com.ssong_develop.core_designsystem.calendar.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.ssong_develop.core_common.extension.addCircleRipple
import com.ssong_develop.core_common.extension.dpToPx
import com.ssong_develop.core_common.extension.getDrawableOrThrow
import com.ssong_develop.core_common.extension.isWeekend
import com.ssong_develop.core_common.extension.toPrettyDateString
import com.ssong_develop.core_common.extension.toPrettyMonthString
import com.ssong_develop.core_common.widget.HorizontalSpacer
import com.ssong_develop.core_designsystem.R
import com.ssong_develop.core_designsystem.calendar.listener.OnClickBeforeMonthListener
import com.ssong_develop.core_designsystem.calendar.listener.OnClickCalendarDayListener
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
    private var selectDay: Date = calendar.time
        set(value) {
            field = value
            onClickCalendarDayListener?.onClick(value)
        }

    private var currentYearAndMonthText: String = calendar.toPrettyMonthString()
        set(value) {
            field = value
            updateYearMonthText(value)
        }

    private val calendarDayAdapter = CalendarDayAdapter { date -> selectDay = date }

    /** Views **/
    private val leftImageButton = ImageView(context).apply {
        id = ViewCompat.generateViewId()
        layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        setImageDrawable(context.getDrawableOrThrow(R.drawable.ic_left_arrow_black_24dp))
        setOnClickListener {
            calendar.add(MONTH, -1)
            updateYearMonthText(calendar.toPrettyMonthString())
            initCalendarData()
            onClickBeforeMonthListener?.onClick()
        }
        addCircleRipple()
    }

    private val yearMonthTexView = TextView(context).apply {
        id = ViewCompat.generateViewId()
        layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
        typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        setTextColor(ContextCompat.getColor(context, R.color.black))
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
        text = currentYearAndMonthText
    }

    private val rightImageButton = ImageView(context).apply {
        id = ViewCompat.generateViewId()
        layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        setImageDrawable(context.getDrawableOrThrow(R.drawable.ic_right_arrow_black_24dp))
        setOnClickListener {
            calendar.add(MONTH, 1)
            updateYearMonthText(calendar.toPrettyMonthString())
            initCalendarData()
            onClickNextMonthListener?.onClick()
        }
        addCircleRipple()
    }

    private val calendarHeaderView = LinearLayout(context).apply {
        id = ViewCompat.generateViewId()
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setPadding(context.dpToPx(6), context.dpToPx(24), 0, context.dpToPx(24))
        addView(leftImageButton)
        addView(HorizontalSpacer(context))
        addView(yearMonthTexView)
        addView(HorizontalSpacer(context))
        addView(rightImageButton)
    }

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
    private var onClickCalendarDayListener: OnClickCalendarDayListener? = null

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL

        addView(calendarHeaderView)
        addView(descriptionView.root)
        addView(calendarDayView)
        initCalendarData()
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {
        /** no - op **/
    }

    private fun initCalendarData() {
        calendarDayAdapter.submitList(getCalendarDayList())
    }

    private fun getCalendarDayList(): List<CalendarDay> {
        val dayOfMonthTargetCalendar = Calendar.getInstance(timeZone, locale).apply {
            set(MONTH, calendar.get(MONTH))
            set(DAY_OF_MONTH, calendar.get(DAY_OF_MONTH))
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

    private fun updateYearMonthText(yearMonthText: String) {
        yearMonthTexView.text = yearMonthText
    }

    /** set listeners **/
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

    fun setOnCalendarDayClickListener(listener: OnClickCalendarDayListener) {
        this.onClickCalendarDayListener = listener
    }

    fun setOnCalendarDayClickListener(block: (date: Date) -> Unit) {
        this.onClickCalendarDayListener = OnClickCalendarDayListener(block)
    }
}