package com.ssong_develop.core_designsystem

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.ssong_develop.core_common.SHORT_ANIMATION_DURATION

class AnimateTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    enum class TextAnimation {
        FADE_IN,
        FADE_OUT,
        SLIDE_DOWN,
        SLIDE_UP
    }

    private val textViewParams = LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    var text = ""
        set(value) {
            field = value
            startTextAnimation()
        }

    @Dimension(unit = Dimension.SP)
    var textSize = 16f
        set(value) {
            field = value / resources.displayMetrics.scaledDensity
            startTextAnimation()
        }

    @ColorRes
    var textColorId: Int = R.color.white
        set(value) {
            field = value
            textColor = ContextCompat.getColor(context, value)
        }

    private var animationType: TextAnimation = TextAnimation.FADE_IN
        set(value) {
            field = value
            startTextAnimation()
        }

    private var textColor = ContextCompat.getColor(context, R.color.white)
        set(value) {
            field = value
            startTextAnimation()
        }

    init {
        orientation = HORIZONTAL

        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {
        context.withStyledAttributes(attrs, R.styleable.AnimateTextView) {
            text = this.getString(R.styleable.AnimateTextView_text) ?: ""
            textSize =
                this.getDimensionPixelSize(R.styleable.AnimateTextView_textSize, 16).toFloat()
            textColorId = this.getResourceId(R.styleable.AnimateTextView_textColor, R.color.white)
            animationType =
                this.getInt(R.styleable.AnimateTextView_animType, TextAnimation.FADE_IN.ordinal)
                    .toTextAnimation()
        }
    }

    private fun startTextAnimation() {
        clearAnimation()
        removeAllViewsInLayout()
        text.forEachIndexed { index, char ->
            val textView = TextView(context).apply {
                text = char.toString()
                textSize = this@AnimateTextView.textSize
                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                setTextColor(this@AnimateTextView.textColor)
                layoutParams = textViewParams
            }

            addView(textView)

            val animation = createTextAnimation().apply {
                duration = SHORT_ANIMATION_DURATION
                startOffset = index * SHORT_ANIMATION_DURATION
            }

            textView.startAnimation(animation)
        }
    }

    private fun createTextAnimation(): Animation = when (animationType) {
        TextAnimation.FADE_IN -> AnimationUtils.loadAnimation(context, R.anim.fade_in)
        TextAnimation.FADE_OUT -> AnimationUtils.loadAnimation(context, R.anim.fade_out)
        TextAnimation.SLIDE_DOWN -> AnimationUtils.loadAnimation(context, R.anim.slide_down)
        TextAnimation.SLIDE_UP -> AnimationUtils.loadAnimation(context, R.anim.slide_up)
    }

    private fun Int.toTextAnimation(): TextAnimation = when (this) {
        TextAnimation.FADE_IN.ordinal -> TextAnimation.FADE_IN
        TextAnimation.FADE_OUT.ordinal -> TextAnimation.FADE_OUT
        TextAnimation.SLIDE_DOWN.ordinal -> TextAnimation.SLIDE_DOWN
        TextAnimation.SLIDE_UP.ordinal -> TextAnimation.SLIDE_UP
        else -> throw IllegalArgumentException("Not valid type")
    }
}