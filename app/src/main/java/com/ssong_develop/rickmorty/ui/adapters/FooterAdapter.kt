package com.ssong_develop.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ItemLoadingFooterBinding
import com.ssong_develop.rickmorty.ui.viewholders.FooterViewHolder

class FooterAdapter : RecyclerView.Adapter<FooterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemLoadingFooterBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_loading_footer, parent, false)
        return FooterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FooterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 2
}