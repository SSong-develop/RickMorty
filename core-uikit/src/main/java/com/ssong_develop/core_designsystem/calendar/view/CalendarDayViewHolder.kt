package com.ssong_develop.core_designsystem.calendar.view

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssong_develop.core_designsystem.R
import com.ssong_develop.core_designsystem.calendar.model.CalendarDay
import com.ssong_develop.core_designsystem.calendar.model.DateType
import com.ssong_develop.core_designsystem.databinding.ViewCalendarDayBinding
import java.util.Date

internal class CalendarDayViewHolder(
    private val binding: ViewCalendarDayBinding,
    private val onDayClick: ((date: Date) -> Unit)
) : ViewHolder(binding.root) {

    private lateinit var dayData: CalendarDay.Day

    init {
        binding.root.setOnClickListener {
            if (this::dayData.isInitialized) {
                when (dayData.dateType) {
                    DateType.DAY, DateType.WEEKEND -> onDayClick(dayData.date)
                    DateType.DISABLED -> {
                        /** no - option **/
                    }
                }
            }
        }
        binding.executePendingBindings()
    }

    fun bindDayState(data: CalendarDay.Day) {
        binding.apply {
            setDayText(data.label)
            setDayTextColor(R.color.black)
        }
    }

    fun bindDisabledState(data: CalendarDay.Day) {
        binding.apply {
            setDayText(data.label)
            setDayTextColor(R.color.gray)
        }
    }

    fun bindWeekendState(data: CalendarDay.Day) {
        binding.apply {
            setDayText(data.label)
            setDayTextColor(R.color.app_bar_color)
        }
    }

    private fun ViewCalendarDayBinding.setDayText(dayText: String) {
        tvDay.text = dayText
    }

    private fun ViewCalendarDayBinding.setDayTextColor(@ColorRes colorId: Int) {
        tvDay.setTextColor(ContextCompat.getColor(this@setDayTextColor.root.context, colorId))
    }
}