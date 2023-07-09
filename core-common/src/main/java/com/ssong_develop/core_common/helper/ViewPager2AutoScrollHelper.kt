package com.ssong_develop.core_common.helper

import android.animation.ValueAnimator
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.core.animation.addListener
import androidx.viewpager2.widget.ViewPager2

class ViewPager2AutoScrollHelper(
    private val viewPager2: ViewPager2
) : AutomaticScrollable {
    override val scrollAnimation: Interpolator =
        AnimationUtils.loadInterpolator(
            viewPager2.context, android.R.interpolator.fast_out_slow_in
        )

    override fun advance() {
        if (viewPager2.width <= 0) return

        val current = viewPager2.currentItem
        val next = ((current + 1) % requireNotNull(viewPager2.adapter).itemCount)
        val pages = next - current
        val dragDistance = pages * viewPager2.width

        ValueAnimator.ofInt(0, dragDistance).apply {
            var dragProgress = 0
            var draggedPages = 0
            addListener(
                onStart = { viewPager2.beginFakeDrag() },
                onEnd = { viewPager2.endFakeDrag() }
            )
            addUpdateListener {
                if (viewPager2.isFakeDragging) {
                    // Sometimes onAnimationUpdate is called with initial value before
                    // onAnimationStart is called.
                    return@addUpdateListener
                }

                val dragPoint = animatedValue as Int
                val dragBy = dragPoint - dragProgress
                viewPager2.fakeDragBy(-dragBy.toFloat())
                dragProgress = dragPoint

                // Fake dragging doesn't let you drag more than one page width. If we want to do
                // this then need to end and start a new fake drag.
                val draggedPagesProgress = dragProgress / viewPager2.width
                if (draggedPagesProgress != draggedPages) {
                    viewPager2.endFakeDrag()
                    viewPager2.beginFakeDrag()
                    draggedPages = draggedPagesProgress
                }
            }
            duration = if (pages == 1) PAGE_CHANGE_DURATION else MULTI_PAGE_CHANGE_DURATION
            interpolator = scrollAnimation
        }.start()
    }

    companion object {
        private const val PAGE_CHANGE_DURATION = 400L
        private const val MULTI_PAGE_CHANGE_DURATION = 600L
    }
}