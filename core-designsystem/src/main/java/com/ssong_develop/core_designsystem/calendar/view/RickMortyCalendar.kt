package com.ssong_develop.core_designsystem.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ssong_develop.core_designsystem.NoRippleRecyclerView
import com.ssong_develop.core_designsystem.calendar.listener.BeforeMonthClickListener
import com.ssong_develop.core_designsystem.calendar.listener.NextMonthClickListener
import com.ssong_develop.core_designsystem.databinding.ViewCalendarWeekDescriptionBinding
import java.util.Calendar
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

    private var beforeMonthClickListener: BeforeMonthClickListener? = null
    private var nextMonthClickListener: NextMonthClickListener? = null

    private val descriptionView = ViewCalendarWeekDescriptionBinding.inflate(
        LayoutInflater.from(context), this, false
    )

    private val header = LinearLayout(context).apply {

    }

    private val calendarView = NoRippleRecyclerView(context).apply {
        id = ViewCompat.generateViewId()
        adapter = calendarAdapter
        layoutManager = GridLayoutManager(context, 7).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = 1
            }
        }
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        setHasFixedSize(true)
    }

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {

    }

}