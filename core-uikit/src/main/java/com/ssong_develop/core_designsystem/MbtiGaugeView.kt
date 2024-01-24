package com.ssong_develop.core_designsystem

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
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 32f
        color = ContextCompat.getColor(context, R.color.app_bar_color)
    }
    private val path = Path()

    var topGauge: Float = 0f
        set(value) {
            field = if (value < 0) {
                0f
            } else {
                value
            }
            updateTopPoint()
            invalidate()
        }

    var bottomGauge: Float = 0f
        set(value) {
            field = if (value < 0) {
                0f
            } else {
                value
            }
            updateBottomPoint()
            invalidate()
        }

    var leftGauge: Float = 0f
        set(value) {
            field = if (value < 0) {
                0f
            } else {
                value
            }
            updateLeftPoint()
            invalidate()
        }

    var rightGauge: Float = 0f
        set(value) {
            field = if (value < 0) {
                0f
            } else {
                value
            }
            updateRightPoint()
            invalidate()
        }

    private var centerPoint: Point = Point(0f, 0f)
        set(value) {
            field = value
            // 각 4방향 포인트 update
            update4wayPoint()
            // Path 갱신
            updatePath()
            invalidate()
        }

    private var topPoint: Point = Point(0f, 0f)

    private var bottomPoint: Point = Point(0f, 0f)

    private var leftPoint: Point = Point(0f, 0f)

    private var rightPoint: Point = Point(0f, 0f)

    init {
        if (attrs != null) {
            getStyleableAttr(attrs)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
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

        if (height == MeasureSpec.EXACTLY) {
            height = heightSize
        } else {
            height = max(height, suggestedMinimumHeight)
            if (heightMode == MeasureSpec.AT_MOST) {
                height = min(heightSize, height)
            }
        }

        updateCenterPoint()
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateCenterPoint()
    }

    override fun onDraw(canvas: Canvas) {
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

    private fun updateCenterPoint() {
        centerPoint = Point((width / 2).toFloat(), (height / 2).toFloat())
    }

    private fun update4wayPoint() {
        updateTopPoint()
        updateBottomPoint()
        updateLeftPoint()
        updateRightPoint()
    }

    private fun updateTopPoint() {
        topPoint = Point(centerPoint.x, centerPoint.y - topGauge)
    }

    private fun updateBottomPoint() {
        bottomPoint = Point(centerPoint.x, centerPoint.y + bottomGauge)
    }

    private fun updateLeftPoint() {
        leftPoint = Point(centerPoint.x + leftGauge, centerPoint.y)
    }

    private fun updateRightPoint() {
        rightPoint = Point(centerPoint.x - rightGauge, centerPoint.y)
    }

    private fun updatePath() {
        path.apply {
            reset()
            moveTo(topPoint.x, topPoint.y)
            lineTo(rightPoint.x, rightPoint.y)
            lineTo(bottomPoint.x, bottomPoint.y)
            lineTo(leftPoint.x, leftPoint.y)
            lineTo(topPoint.x, topPoint.y)
        }
    }

    data class Point(
        val x: Float,
        val y: Float
    )
}