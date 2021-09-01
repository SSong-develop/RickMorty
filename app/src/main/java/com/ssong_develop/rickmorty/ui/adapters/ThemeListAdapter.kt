package com.ssong_develop.rickmorty.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ItemMainThemeBinding
import com.ssong_develop.rickmorty.entities.Theme
import com.ssong_develop.rickmorty.ui.viewholders.ThemeListViewHolder

class ThemeListAdapter(
    private val delegate: ThemeListViewHolder.Delegate
) : RecyclerView.Adapter<ThemeListViewHolder>() {

    private val data = mutableListOf<Theme>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemMainThemeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_main_theme, parent, false)
        return ThemeListViewHolder(binding, delegate)
    }

    override fun onBindViewHolder(holder: ThemeListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(itemList : List<Theme>){
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }
}

@BindingAdapter("theme_item")
fun RecyclerView.setThemeItem(items : List<Theme>){
    (adapter as? ThemeListAdapter)?.run {
        submitList(items)
    }
}