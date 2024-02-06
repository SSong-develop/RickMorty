package com.ssong_develop.core_designsystem

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import kotlin.math.max
import kotlin.math.min

class MbtiGaugeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 32f
        color = ContextCompat.getColor(context, R.color.app_bar_color)
    }
    private val path = Path()
    private val pathAnimator: ValueAnimator? = null
    // TODO 애니메이터를 list로 관리해서 묶고 있을까..?

    var topGauge: Float = 0f
        set(value) {
            field = value
            updateTopPoint()
            startPathAnimation()
        }

    var bottomGauge: Float = 0f
        set(value) {
            field = value
            updateBottomPoint()
            startPathAnimation()
        }

    var leftGauge: Float = 0f
        set(value) {
            field = value
            updateLeftPoint()
            startPathAnimation()
        }

    var rightGauge: Float = 0f
        set(value) {
            field = value
            updateRightPoint()
            startPathAnimation()
        }

    private var centerPoint = Point(0.0, 0.0)
        set(value) {
            field = value
            invalidate()
        }

    private var topPoint = Point(0.0, 0.0)

    private var bottomPoint = Point(0.0, 0.0)

    private var leftPoint = Point(0.0, 0.0)

    private var rightPoint = Point(0.0, 0.0)

    init {
        if (attrs != null) {
            getStyleableAttr(attrs)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 가로 길이 측정 모드 값
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        // 세로 길이 측정 모드 값
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        // 가로 길이
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        // 세로 길이
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var width = paddingLeft + paddingRight
        var height = paddingTop + paddingBottom

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize
        } else {
            width = max(width, suggestedMinimumWidth)
            if (widthMode == MeasureSpec.AT_MOST) {
                width = min(widthSize, width)
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize
        } else {
            height = max(height, suggestedMinimumHeight)
            if (heightMode == MeasureSpec.AT_MOST) {
                height = min(heightSize, height)
            }
        }

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        updateCenterPoint(width, height)
        updateTopPoint()
        updateBottomPoint()
        updateLeftPoint()
        updateRightPoint()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateCenterPoint(width, height)
        updateTopPoint()
        updateBottomPoint()
        updateLeftPoint()
        updateRightPoint()
        updatePath()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
    }

    private fun getStyleableAttr(attrs: AttributeSet) {
        context.withStyledAttributes(attrs, R.styleable.MbtiGaugeView) {
            topGauge = getFloat(R.styleable.MbtiGaugeView_top_gauge, 0f)
            bottomGauge = getFloat(R.styleable.MbtiGaugeView_bottom_gauge, 0f)
            leftGauge = getFloat(R.styleable.MbtiGaugeView_left_gauge, 0f)
            rightGauge = getFloat(R.styleable.MbtiGaugeView_right_gauge, 0f)
        }
    }

    private fun updatePath() {
        path.apply {
            reset()
            moveTo(topPoint.x.toFloat(), topPoint.y.toFloat())
            lineTo(rightPoint.x.toFloat(), rightPoint.y.toFloat())
            lineTo(bottomPoint.x.toFloat(), bottomPoint.y.toFloat())
            lineTo(leftPoint.x.toFloat(), leftPoint.y.toFloat())
            lineTo(topPoint.x.toFloat(), topPoint.y.toFloat())
        }
    }

    private fun updateCenterPoint(width: Int, height: Int) {
        centerPoint = Point((width / 2).toDouble(), (height / 2).toDouble())
    }

    private fun updateTopPoint() {
        topPoint = Point(centerPoint.x, centerPoint.y - (topGauge / 100.0) * centerPoint.y)
    }

    private fun updateBottomPoint() {
        bottomPoint = Point(centerPoint.x, centerPoint.y + (bottomGauge / 100.0) * centerPoint.y)
    }

    private fun updateLeftPoint() {
        leftPoint = Point(centerPoint.x - (leftGauge / 100.0) * centerPoint.x, centerPoint.y)
    }

    private fun updateRightPoint() {
        rightPoint = Point(centerPoint.x + (rightGauge / 100.0) * centerPoint.x, centerPoint.y)
    }

    private fun startPathAnimation() {

    }

    data class Point(
        val x: Double, val y: Double
    )
}