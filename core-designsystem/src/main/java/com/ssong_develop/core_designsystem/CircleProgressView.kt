package com.ssong_develop.core_designsystem

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator

class CircleProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var maxProgress = 30f

    private lateinit var progressBounds: RectF
    private var sweepAngle: Float = 250f
    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private val progressPaint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val backgroundProgressPaint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val textPaint: Paint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create("cabin",Typeface.BOLD)
    }

    private val animationInterpolator by lazy { DecelerateInterpolator() }

    private var radius = 0.0f

    var progressBackgroundColor = Color.parseColor("#EFEFEF")
    var progressColor = Color.parseColor("#4169e1")

    var default = 0

    var progress: Int = 0
        set(value) {
            field = value
            ValueAnimator.ofFloat(sweepAngle, 360f/ maxProgress * value).apply {
                interpolator = animationInterpolator
                duration = 300
                addUpdateListener { animation ->
                    sweepAngle = animation.animatedValue as Float
                    invalidate()
                }
                start()
            }
        }

    init {
        isClickable = true

        if (attrs != null) {
            getStyleableAttr(attrs)
        }

        progress = default
    }

    private fun getStyleableAttr(attrs: AttributeSet) {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // draw progressBar background
        canvas.drawArc(progressBounds, 90f, 360f, false, backgroundProgressPaint)
        // draw progress
        canvas.drawArc(progressBounds, 90f, sweepAngle, false, progressPaint)
        // draw progress Text
        canvas.drawText("$progress", centerX, centerY, textPaint)
    }
}