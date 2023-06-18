package com.ssong_develop.feature_character.presentation.character.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.presentation.character.viewholders.FooterViewHolder
import com.ssong_develop.feature_character.databinding.ItemLoadingFooterBinding
import java.util.*

class FooterAdapter(val context: Context) : RecyclerView.Adapter<FooterViewHolder>() {

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
    ): FooterViewHolder {
        val binding = DataBindingUtil.bind<ItemLoadingFooterBinding>(
            runCatching {
                cachedLoadingView.pop().also {
                    createAsynchronousLoadingView()
                }
            }.getOrElse {
                val inflater = LayoutInflater.from(parent.context)
                inflater.inflate(R.layout.item_loading_footer, parent, false)
            }
        ) ?: DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_loading_footer,
            parent,
            false
        )
        return FooterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FooterViewHolder,
        position: Int
    ) {
        holder.bind()
    }

    override fun getItemCount(): Int = FOOTER_VIEW_COUNT

    private fun createAsynchronousLoadingView() {
        asyncLayoutInflater.inflate(
            R.layout.item_loading_footer,
            null
        ) { view, _, _ ->
            cachedLoadingView.push(view)
        }
    }

    companion object {
        private const val FOOTER_VIEW_COUNT = 1
    }
}