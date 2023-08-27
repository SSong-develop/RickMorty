package com.ssong_develop.core_designsystem

import android.content.Context
import android.graphics.Color
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

class FadeInTextView @JvmOverloads constructor(
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
            startFadeInAnimation()
        }

    @Dimension(unit = Dimension.SP)
    var textSize = 16f
        set(value) {
            field = value / resources.displayMetrics.scaledDensity
            startFadeInAnimation()
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
            startFadeInAnimation()
        }

    init {
        orientation = HORIZONTAL

        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {
        context.withStyledAttributes(attrs, R.styleable.FadeInTextView) {
            text = this.getString(R.styleable.FadeInTextView_text) ?: ""
            textSize = this.getDimensionPixelSize(R.styleable.FadeInTextView_textSize, 16).toFloat()
            textColorId = this.getResourceId(R.styleable.FadeInTextView_textColor, R.color.white)
        }
    }

    private fun startFadeInAnimation() {
        removeAllViewsInLayout()
        text.forEachIndexed { index, char ->
            val textView = TextView(context).apply {
                text = char.toString()
                textSize = this@FadeInTextView.textSize
                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                setTextColor(this@FadeInTextView.textColor)
                layoutParams = textViewParams
            }

            addView(textView)

            val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in).apply {
                duration = 1000L
                startOffset = (index * 200).toLong()
            }

            textView.startAnimation(fadeInAnimation)
        }
    }
}