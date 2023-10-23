package com.ssong_develop.feature_favorite.view.calendar.view.viewholders

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssong_develop.core_common.extension.dpToPx
import com.ssong_develop.core_common.extension.toInvertedColor
import com.ssong_develop.core_designsystem.R
import com.ssong_develop.feature_favorite.databinding.ViewCalendarDayBinding
import com.ssong_develop.feature_favorite.view.calendar.model.CalendarDay
import com.ssong_develop.feature_favorite.view.calendar.model.DateType
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
            tvDay.setTextColor(ContextCompat.getColor(root.context, R.color.white))
            ivFavCharacterEpisodeAirDateIndicator.isVisible = false
        }
    }

    fun bindEpisodeAirDayState(data: CalendarDay.Day, dominantColor: Int) {
        binding.apply {
            val airDateIndicateDrawable = ShapeDrawable(OvalShape()).apply {
                paint.color = dominantColor
                intrinsicWidth = root.context.dpToPx(32)
                intrinsicHeight = root.context.dpToPx(32)
            }
            tvDay.text = data.label
            tvDay.setTextColor(dominantColor.toColor().toInvertedColor())
            ivFavCharacterEpisodeAirDateIndicator.setImageDrawable(airDateIndicateDrawable)
            ivFavCharacterEpisodeAirDateIndicator.isVisible = true
        }
    }

    fun bindDisabledState(data: CalendarDay.Day) {
        binding.apply {
            tvDay.text = data.label
            tvDay.setTextColor(ContextCompat.getColor(root.context, R.color.gray))
            ivFavCharacterEpisodeAirDateIndicator.isVisible = false
        }
    }

    fun bindWeekendState(data: CalendarDay.Day) {
        binding.apply {
            tvDay.text = data.label
            tvDay.setTextColor(ContextCompat.getColor(root.context, R.color.app_bar_color))
            ivFavCharacterEpisodeAirDateIndicator.isVisible = false
        }
    }
}