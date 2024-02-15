package com.ssong_develop.core_designsystem

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.content.withStyledAttributes
import com.ssong_develop.core_common.extension.dpToPx
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class RhombusChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var topPercent: Float = 0f
        set(value) {
            updateTopPointAnimator(field, value)
            field = value
            updateTopPoint()
        }

    var bottomPercent: Float = 0f
        set(value) {
            updateBottomPointAnimator(field, value)
            field = value
            updateBottomPoint()
        }

    var leftPercent: Float = 0f
        set(value) {
            updateLeftPointAnimator(field, value)
            field = value
            updateLeftPoint()
        }

    var rightPercent: Float = 0f
        set(value) {
            updateRightPointAnimator(field, value)
            field = value
            updateRightPoint()
        }

    @ColorInt
    var chartColor = Color.BLACK
        set(value) {
            field = value
            updateChartPaint()
        }

    @Px
    var chartWidth = context.dpToPx(4f)
        set(value) {
            field = value
            updateChartPaint()
        }

    /* paint & path */
    private val chartPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = chartWidth.toFloat()
        color = chartColor
    }

    private val chartPath = Path()

    /* points */
    private var centerPoint = Point(0.0, 0.0)
        set(value) {
            field = value
            invalidate()
        }

    private var topPoint = Point(0.0, 0.0)

    private var bottomPoint = Point(0.0, 0.0)

    private var leftPoint = Point(0.0, 0.0)

    private var rightPoint = Point(0.0, 0.0)

    /* animator */
    private var topPointAnimator: ValueAnimator? = null

    private var bottomPointAnimator: ValueAnimator? = null

    private var leftPointAnimator: ValueAnimator? = null

    private var rightPointAnimator: ValueAnimator? = null

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
        updateChartPath()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawChart(canvas)
    }

    private fun getStyleableAttr(attrs: AttributeSet) {
        context.withStyledAttributes(attrs, R.styleable.RhombusChartView) {
            topPercent = getFloat(R.styleable.RhombusChartView_top_percent, 0f)
            bottomPercent = getFloat(R.styleable.RhombusChartView_bottom_percent, 0f)
            leftPercent = getFloat(R.styleable.RhombusChartView_left_percent, 0f)
            rightPercent = getFloat(R.styleable.RhombusChartView_right_percent, 0f)
            chartColor = getColor(R.styleable.RhombusChartView_chart_color, Color.BLACK)
            chartWidth = getDimension(
                R.styleable.RhombusChartView_chart_width,
                context.dpToPx(4f).toFloat()
            ).roundToInt()
        }
    }

    private fun updateChartPaint() {
        chartPaint.apply {
            strokeWidth = chartWidth.toFloat()
            color = chartColor
        }
    }

    private fun updateChartPath() {
        chartPath.apply {
            reset()
            moveTo(topPoint.x.toFloat(), topPoint.y.toFloat())
            lineTo(rightPoint.x.toFloat(), rightPoint.y.toFloat())
            lineTo(bottomPoint.x.toFloat(), bottomPoint.y.toFloat())
            lineTo(leftPoint.x.toFloat(), leftPoint.y.toFloat())
            lineTo(topPoint.x.toFloat(), topPoint.y.toFloat())
            lineTo(rightPoint.x.toFloat(), rightPoint.y.toFloat())
        }
    }

    private fun updateCenterPoint(width: Int, height: Int) {
        centerPoint = Point((width / 2).toDouble(), (height / 2).toDouble())
    }

    private fun updateTopPoint() {
        topPoint = Point(centerPoint.x, centerPoint.y - (topPercent / 100.0) * centerPoint.y)
    }

    private fun updateBottomPoint() {
        bottomPoint = Point(centerPoint.x, centerPoint.y + (bottomPercent / 100.0) * centerPoint.y)
    }

    private fun updateLeftPoint() {
        leftPoint = Point(centerPoint.x - (leftPercent / 100.0) * centerPoint.x, centerPoint.y)
    }

    private fun updateRightPoint() {
        rightPoint = Point(centerPoint.x + (rightPercent / 100.0) * centerPoint.x, centerPoint.y)
    }

    private fun updateTopPointAnimator(prev: Float, current: Float) {
        if (topPointAnimator?.isRunning == true) {
            topPointAnimator?.cancel()
            topPointAnimator = null
        }
        topPointAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000L
            interpolator = AccelerateInterpolator()
            addUpdateListener {
                val percent = it.animatedValue as Float
                val gauge = prev + ((current - prev) * percent)
                topPoint = Point(centerPoint.x, centerPoint.y - (gauge / 100.0) * centerPoint.y)
                invalidate()
            }
        }
        topPointAnimator?.start()
    }

    private fun updateBottomPointAnimator(prev: Float, current: Float) {
        if (bottomPointAnimator?.isRunning == true) {
            bottomPointAnimator?.cancel()
            bottomPointAnimator = null
        }
        bottomPointAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000L
            interpolator = AccelerateInterpolator()
            addUpdateListener {
                val percent = it.animatedValue as Float
                val gauge = prev + ((current - prev) * percent)
                bottomPoint = Point(centerPoint.x, centerPoint.y + (gauge / 100.0) * centerPoint.y)
                invalidate()
            }
        }
        bottomPointAnimator?.start()
    }

    private fun updateLeftPointAnimator(prev: Float, current: Float) {
        if (leftPointAnimator?.isRunning == true) {
            leftPointAnimator?.cancel()
            leftPointAnimator = null
        }
        leftPointAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000L
            interpolator = AccelerateInterpolator()
            addUpdateListener {
                val percent = it.animatedValue as Float
                val gauge = prev + ((current - prev) * percent)
                leftPoint = Point(centerPoint.x - (gauge / 100.0) * centerPoint.x, centerPoint.y)
                invalidate()
            }
        }
        leftPointAnimator?.start()
    }

    private fun updateRightPointAnimator(prev: Float, current: Float) {
        if (rightPointAnimator?.isRunning == true) {
            rightPointAnimator?.cancel()
            rightPointAnimator = null
        }
        rightPointAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000L
            interpolator = AccelerateInterpolator()
            addUpdateListener {
                val percent = it.animatedValue as Float
                val gauge = prev + ((current - prev) * percent)
                rightPoint = Point(centerPoint.x + (gauge / 100.0) * centerPoint.x, centerPoint.y)
                invalidate()
            }
        }
        rightPointAnimator?.start()
    }

    private fun drawChart(canvas: Canvas) {
        updateChartPath()
        canvas.drawPath(chartPath, chartPaint)
    }

    data class Point(
        val x: Double, val y: Double
    )
}