package com.ssong_develop.core_designsystem.calendar.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssong_develop.core_designsystem.calendar.listener.CalendarDayClickListener
import com.ssong_develop.core_designsystem.calendar.model.CalendarDay
import com.ssong_develop.core_designsystem.calendar.model.CalendarDayType
import com.ssong_develop.core_designsystem.calendar.model.DateType
import com.ssong_develop.core_designsystem.databinding.ViewCalendarDayBinding
import com.ssong_develop.core_designsystem.databinding.ViewCalendarEmptyBinding
import java.lang.IllegalStateException
import java.util.Calendar
import java.util.Date

private val calendarItemDiffUtils = object : DiffUtil.ItemCallback<CalendarDay>() {
    override fun areItemsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean =
        oldItem == newItem

}

internal class CalendarAdapter(
    private val onDayClick: (date: Date) -> Unit
): ListAdapter<CalendarDay, ViewHolder>(calendarItemDiffUtils) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CalendarDayType.DAY.ordinal -> {
                val dayCalendarBinding : ViewCalendarDayBinding = ViewCalendarDayBinding.inflate(layoutInflater)
                return CalendarDayViewHolder(dayCalendarBinding, onDayClick)
            }
            CalendarDayType.EMPTY.ordinal -> {
                val emptyCalendarBinding : ViewCalendarEmptyBinding = ViewCalendarEmptyBinding.inflate(layoutInflater)
                return CalendarEmptyViewHolder(emptyCalendarBinding)
            }
            else -> {
                throw IllegalStateException("Not Valid Type")
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder.itemViewType) {
            CalendarDayType.DAY.ordinal -> {
                val calendarDayViewHolder = holder as CalendarDayViewHolder
                val calendarDayItem = getItem(position) as CalendarDay.Day

                when (calendarDayItem.dateType) {
                    DateType.DAY -> calendarDayViewHolder.bindDayState(calendarDayItem)
                    DateType.DISABLED -> calendarDayViewHolder.bindDisabledState(calendarDayItem)
                    DateType.WEEKEND -> calendarDayViewHolder.bindWeekendState(calendarDayItem)
                }
            }
            CalendarDayType.EMPTY.ordinal -> {
                val emptyCalendarViewHolder = holder as CalendarEmptyViewHolder
                val calendarEmptyItem = getItem(position) as CalendarDay.Empty
                emptyCalendarViewHolder.bind(calendarEmptyItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal
}