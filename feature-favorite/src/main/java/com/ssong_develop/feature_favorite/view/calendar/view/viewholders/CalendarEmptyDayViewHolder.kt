package com.ssong_develop.feature_favorite.view.calendar.view.viewholders

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssong_develop.feature_favorite.databinding.ViewCalendarEmptyBinding
import com.ssong_develop.feature_favorite.view.calendar.model.CalendarDay

internal class CalendarEmptyDayViewHolder(
    private val binding: ViewCalendarEmptyBinding
) : ViewHolder(binding.root) {

    private lateinit var emptyDayData: CalendarDay.Empty

    fun bind(data: CalendarDay.Empty) {
        emptyDayData = data
        binding.apply {
            tvEmptyDay.text = emptyDayData.label
            executePendingBindings()
        }
    }
}