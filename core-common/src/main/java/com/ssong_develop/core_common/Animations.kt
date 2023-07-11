package com.ssong_develop.core_common

import android.animation.Animator
import android.view.View

fun View.animateSlideUp(animatorListener: Animator.AnimatorListener) {
    this.animate()
        .translationY(-(this.height.toFloat()))
        .alpha(0f)
        .setListener(animatorListener)
}

fun View.animateSlideDown(animatorListener: Animator.AnimatorListener) {
    this.animate()
        .translationY(this.height.toFloat())
        .alpha(0f)
        .setListener(animatorListener)
}