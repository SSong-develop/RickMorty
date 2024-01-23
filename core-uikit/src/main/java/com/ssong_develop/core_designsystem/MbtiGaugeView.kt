package com.ssong_develop.core_designsystem

import android.content.Context
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import kotlin.math.min

class MbtiGaugeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var topGauge: Int = 0

    var bottomGauge: Int = 0

    var leftGauge: Int = 0

    var rightGauge: Int = 0

    var topPoint: Point = Point(0, 0)
        set(value) {
            val oldValue = field
            if (oldValue != value) {
                // TODO start Animate
            }

            field = value
        }

    var bottomPoint: Point = Point(0, 0)
        set(value) {
            val oldValue = field
            if (oldValue != value) {
                // TODO start Animate
            }

            field = value
        }

    var leftPoint: Point = Point(0, 0)
        set(value) {
            val oldValue = field
            if (oldValue != value) {
                // TODO start Animate
            }

            field = value
        }

    var rightPoint: Point = Point(0, 0)
        set(value) {
            val oldValue = field
            if (oldValue != value) {
                // TODO start Animate
            }

            field = value
        }

    var centerPoint: Point = Point(0, 0)

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

        // TODO : center Position 값 구해서 넣어줘야 합니다.
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    fun updateCenterPoint(x: Int, y: Int) {
        centerPoint = Point(x, y)
    }

    fun updateTopPoint(x: Int, y: Int) {
        topPoint = Point(x, y)
    }

    fun updateBottomPoint(x: Int, y: Int) {
        bottomPoint = Point(x, y)
    }

    fun updateLeftPoint(x: Int, y: Int) {
        leftPoint = Point(x, y)
    }

    fun updateRightPoint(x: Int, y: Int) {
        rightPoint = Point(x, y)
    }


    data class Point(
        val x: Int,
        val y: Int
    )
}