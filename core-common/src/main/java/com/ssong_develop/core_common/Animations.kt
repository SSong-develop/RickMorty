package com.ssong_develop.core_common

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.BounceInterpolator

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

fun View.animateScaleUp(
    scaleSize: Float
) {
    require(scaleSize > 1.0f) { return }

    val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, 1f, scaleSize)
    val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, 1f, scaleSize)

    val translationY = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, -200f, 0f)

    AnimatorSet().apply {
        duration = 500L
        interpolator = BounceInterpolator()
        play(scaleX).with(scaleY).with(translationY)
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}

            override fun onAnimationEnd(p0: Animator) {
                this@animateScaleUp.scaleX = 1.0f
                this@animateScaleUp.scaleY = 1.0f
            }

            override fun onAnimationCancel(p0: Animator) {
                this@animateScaleUp.scaleX = 1.0f
                this@animateScaleUp.scaleY = 1.0f
            }

            override fun onAnimationRepeat(p0: Animator) {}
        })
    }.start()
}

fun View.animateScaleDown(
    scaleSize: Float
) {
    require(scaleSize < 1.0f) { return }

    val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, 1f, scaleSize)
    val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, 1f, scaleSize)

    val translationY = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, -200f, 0f)

    AnimatorSet().apply {
        duration = 500L
        interpolator = BounceInterpolator()
        play(scaleX).with(scaleY).with(translationY)
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}

            override fun onAnimationEnd(p0: Animator) {
                this@animateScaleDown.scaleX = 1.0f
                this@animateScaleDown.scaleY = 1.0f
            }

            override fun onAnimationCancel(p0: Animator) {
                this@animateScaleDown.scaleX = 1.0f
                this@animateScaleDown.scaleY = 1.0f
            }

            override fun onAnimationRepeat(p0: Animator) {}
        })
    }.start()
}