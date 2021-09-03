package com.ssong_develop.rickmorty.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.databinding.ItemMainThemeBinding
import com.ssong_develop.rickmorty.entities.Theme

class ThemeListViewHolder(
    private val binding: ItemMainThemeBinding,
    private val delegate: Delegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var theme: Theme

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
        binding.root.apply {
            scaleX = 0.9f
        }
    }

    interface Delegate {
        fun onItemClick(view: View, theme: Theme)
    }

    fun bind(data: Theme) {
        theme = data
        binding.apply {
            theme = data
            executePendingBindings()
        }
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(binding.tvMain, theme)
    }

    override fun onLongClick(v: View?): Boolean = false
}