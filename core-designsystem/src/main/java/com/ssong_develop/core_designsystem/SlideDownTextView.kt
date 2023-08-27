package com.ssong_develop.core_designsystem

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.ssong_develop.core_common.SHORT_ANIMATION_DURATION

/**
 * TextView as show slideDown animation effect
 */
class SlideDownTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val textViewParams = LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    var text = ""
        set(value) {
            field = value
            startSlideDownAnimation()
        }

    @Dimension(unit = Dimension.SP)
    var textSize = 16f
        set(value) {
            field = value / resources.displayMetrics.scaledDensity
            startSlideDownAnimation()
        }

    @ColorRes
    var textColorId: Int = R.color.white
        set(value) {
            field = value
            textColor = ContextCompat.getColor(context, value)
        }

    private var textColor = ContextCompat.getColor(context, R.color.white)
        set(value) {
            field = value
            startSlideDownAnimation()
        }

    init {
        orientation = HORIZONTAL

        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {
        context.withStyledAttributes(attrs, R.styleable.SlideDownTextView) {
            text = this.getString(R.styleable.SlideDownTextView_text) ?: ""
            textSize =
                this.getDimensionPixelSize(R.styleable.SlideDownTextView_textSize, 16).toFloat()
            textColorId = this.getResourceId(R.styleable.SlideDownTextView_textColor, R.color.white)
        }
    }

    private fun startSlideDownAnimation() {
        removeAllViewsInLayout()
        text.forEachIndexed { index, char ->
            val textView = TextView(context).apply {
                text = char.toString()
                textSize = this@SlideDownTextView.textSize
                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                setTextColor(this@SlideDownTextView.textColor)
                layoutParams = textViewParams
            }

            addView(textView)

            val slideDownAnimation =
                AnimationUtils.loadAnimation(context, R.anim.slide_down).apply {
                    startOffset = index * SHORT_ANIMATION_DURATION
                }
            textView.startAnimation(slideDownAnimation)
        }
    }
}