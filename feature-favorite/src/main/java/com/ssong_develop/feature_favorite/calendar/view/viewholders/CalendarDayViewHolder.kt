package com.ssong_develop.feature_favorite.calendar.view.viewholders

import android.graphics.ColorFilter
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssong_develop.core_designsystem.R
import com.ssong_develop.feature_favorite.calendar.model.CalendarDay
import com.ssong_develop.feature_favorite.calendar.model.DateType
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
            tvDay.text = data.label
            tvDay.setTextColor(ContextCompat.getColor(root.context, R.color.black))
            ivFavCharacterEpisodeAirDateIndicator.setImageDrawable(ShapeDrawable(OvalShape()))
        }
    }

    fun bindDisabledState(data: CalendarDay.Day) {
        binding.apply {
            tvDay.text = data.label
            tvDay.setTextColor(ContextCompat.getColor(root.context, R.color.gray))
        }
    }

    fun bindWeekendState(data: CalendarDay.Day) {
        binding.apply {
            tvDay.text = data.label
            tvDay.setTextColor(ContextCompat.getColor(root.context, R.color.app_bar_color))
        }
    }
}