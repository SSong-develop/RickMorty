package com.ssong_develop.core_designsystem.calendar.view

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssong_develop.core_designsystem.calendar.model.CalendarDay
import com.ssong_develop.core_designsystem.databinding.ViewCalendarEmptyBinding

internal class CalendarEmptyDayViewHolder(
    private val binding: ViewCalendarEmptyBinding
) : ViewHolder(binding.root) {

    private lateinit var emptyDayData: CalendarDay.Empty

    fun bind(data: CalendarDay.Empty) {
        /** no - op **/
    }
}