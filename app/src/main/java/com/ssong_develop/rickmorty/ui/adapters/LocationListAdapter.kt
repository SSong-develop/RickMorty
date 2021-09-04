package com.ssong_develop.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ItemLocationBinding
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.ui.viewholders.LocationListViewHolder

private val locationDiffItemCallback = object : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean =
        oldItem.id == newItem.id
}

class LocationListAdapter(
    private val delegate: LocationListViewHolder.Delegate
) : ListAdapter<Location, LocationListViewHolder>(locationDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemLocationBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_location,parent,false)
        return LocationListViewHolder(binding,delegate)
    }

    override fun onBindViewHolder(holder: LocationListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

@BindingAdapter("location_item")
fun RecyclerView.setLocationItem(list : List<Location>?){
    (adapter as LocationListAdapter)?.run {
        submitList(list)
    }
}