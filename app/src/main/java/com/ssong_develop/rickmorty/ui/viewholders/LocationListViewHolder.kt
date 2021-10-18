package com.ssong_develop.rickmorty.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.databinding.ItemLocationBinding
import com.ssong_develop.rickmorty.entities.Location

class LocationListViewHolder(
    private val binding: ItemLocationBinding,
    private val delegate: Delegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    lateinit var location: Location

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    interface Delegate {
        fun onItemClick(view: View, location: Location)
    }

    fun bind(data: Location) {
        location = data
        binding.apply {
            location = data
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClick(view, location)
    }

    override fun onLongClick(v: View?): Boolean = false
}