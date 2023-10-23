package com.ssong_develop.feature_favorite.view.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ssong_develop.feature_favorite.R

class CalendarDayRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): RecyclerView(context, attrs, defStyle) {

    init {
        overScrollMode = OVER_SCROLL_NEVER
        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        val scaleUpAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.calendar_anim)
        layoutAnimation = scaleUpAnimation
        layoutManager = GridLayoutManager(context, 7).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = 1
            }
        }
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        setHasFixedSize(true)
    }
}