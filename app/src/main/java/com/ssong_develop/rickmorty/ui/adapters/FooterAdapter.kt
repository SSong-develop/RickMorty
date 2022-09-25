package com.ssong_develop.rickmorty.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ItemLoadingFooterBinding
import com.ssong_develop.rickmorty.ui.viewholders.FooterViewHolder
import java.util.*

class FooterAdapter(val context: Context) : RecyclerView.Adapter<FooterViewHolder>() {

    companion object {
        private const val FOOTER_VIEW_COUNT = 2
    }

    private val asyncLayoutInflater = AsyncLayoutInflater(context)

    private val cachedView = Stack<View>()

    init {
        repeat(FOOTER_VIEW_COUNT) {
            asyncLayoutInflater.inflate(
                R.layout.item_loading_footer,
                null
            ) { view, layoutRes, viewGroup ->
                cachedView.push(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterViewHolder {
        val binding = DataBindingUtil.bind<ItemLoadingFooterBinding>(cachedView.pop().also {
            asyncLayoutInflater.inflate(
                R.layout.item_loading_footer,
                null
            ) { view, layoutRes, viewGroup ->
                cachedView.push(view)
            }
        }) ?: DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_loading_footer,
            parent,
            false
        )
        return FooterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FooterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 2
}