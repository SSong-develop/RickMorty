package com.ssong_develop.feature_favorite.view.calendar.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssong_develop.core_common.extension.isTheSameDay
import com.ssong_develop.core_model.RickMortyCharacter
import com.ssong_develop.feature_favorite.R
import com.ssong_develop.feature_favorite.databinding.ViewCalendarDayBinding
import com.ssong_develop.feature_favorite.databinding.ViewCalendarEmptyBinding
import com.ssong_develop.feature_favorite.view.calendar.model.CalendarDay
import com.ssong_develop.feature_favorite.view.calendar.model.CalendarDayType
import com.ssong_develop.feature_favorite.view.calendar.model.DateType
import com.ssong_develop.feature_favorite.view.calendar.view.viewholders.CalendarDayViewHolder
import com.ssong_develop.feature_favorite.view.calendar.view.viewholders.CalendarEmptyDayViewHolder
import java.util.Date
import kotlin.properties.Delegates

private val calendarItemDiffUtils = object : DiffUtil.ItemCallback<CalendarDay>() {
    override fun areItemsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean =
        oldItem == newItem
}

@SuppressLint("NotifyDataSetChanged")
internal class CalendarDayAdapter(
    private val onDayClick: (date: Date) -> Unit
) : ListAdapter<CalendarDay, ViewHolder>(calendarItemDiffUtils) {

    private var favCharacter: RickMortyCharacter? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            notifyDataSetChanged()
        }
    }

    private val episodeAirDates: MutableList<Date> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CalendarDayType.DAY.ordinal -> {
                val dayCalendarBinding: ViewCalendarDayBinding =
                    ViewCalendarDayBinding.inflate(layoutInflater)
                CalendarDayViewHolder(dayCalendarBinding, onDayClick)
            }

            CalendarDayType.EMPTY.ordinal -> {
                val emptyCalendarBinding: ViewCalendarEmptyBinding =
                    ViewCalendarEmptyBinding.inflate(layoutInflater)
                CalendarEmptyDayViewHolder(emptyCalendarBinding)
            }

            else -> {
                throw IllegalStateException("Not Valid Type")
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CalendarDayType.DAY.ordinal -> {
                val calendarDayViewHolder = holder as CalendarDayViewHolder
                val calendarDayItem = getItem(position) as CalendarDay.Day

                when (calendarDayItem.dateType) {
                    DateType.DAY -> {
                        if (episodeAirDates.isEmpty()) {
                            calendarDayViewHolder.bindDayState(calendarDayItem)
                        } else {
                            if (episodeAirDates.any { it.isTheSameDay(calendarDayItem.date) }) {
                                calendarDayViewHolder.bindEpisodeAirDayState(
                                    calendarDayItem,
                                    favCharacter?.dominantColor ?: ContextCompat.getColor(
                                        holder.itemView.context,
                                        R.color.app_bar_color
                                    )
                                )
                            } else {
                                calendarDayViewHolder.bindDayState(calendarDayItem)
                            }
                        }
                    }

                    DateType.DISABLED -> calendarDayViewHolder.bindDisabledState(calendarDayItem)
                    DateType.WEEKEND -> calendarDayViewHolder.bindWeekendState(calendarDayItem)
                }
            }

            CalendarDayType.EMPTY.ordinal -> {
                val emptyCalendarViewHolder = holder as CalendarEmptyDayViewHolder
                val calendarEmptyItem = getItem(position) as CalendarDay.Empty
                emptyCalendarViewHolder.bind(calendarEmptyItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal

    fun initFavCharacter(character: RickMortyCharacter) {
        favCharacter = character
    }

    fun submitEpisodeAirDates(airDates: List<Date>) {
        episodeAirDates.clear()
        episodeAirDates.addAll(airDates)
        notifyDataSetChanged()
    }
}