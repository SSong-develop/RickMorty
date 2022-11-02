package com.ssong_develop.feature_character.character.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.databinding.ItemLoadingFooterBinding
import java.util.*

class FooterAdapter(val context: Context) :
    RecyclerView.Adapter<com.ssong_develop.feature_character.character.viewholders.FooterViewHolder>() {
    companion object {
        private const val FOOTER_VIEW_COUNT = 2
    }

    private val asyncLayoutInflater = AsyncLayoutInflater(context)

    private val cachedLoadingView = Stack<View>()

    init {
        repeat(FOOTER_VIEW_COUNT) {
            createAsynchronousLoadingView()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): com.ssong_develop.feature_character.character.viewholders.FooterViewHolder {
        val binding = DataBindingUtil.bind<ItemLoadingFooterBinding>(cachedLoadingView.pop().also {
            createAsynchronousLoadingView()
        }) ?: DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_loading_footer,
            parent,
            false
        )
        return com.ssong_develop.feature_character.character.viewholders.FooterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: com.ssong_develop.feature_character.character.viewholders.FooterViewHolder,
        position: Int
    ) {
        holder.bind()
    }

    override fun getItemCount(): Int = 2

    private fun createAsynchronousLoadingView() {
        asyncLayoutInflater.inflate(
            R.layout.item_loading_footer,
            null
        ) { view, _, _ ->
            cachedLoadingView.push(view)
        }
    }
}