package com.ssong_develop.core_designsystem

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.graphics.Color.alpha
import android.graphics.Color.argb
import android.graphics.Color.blue
import android.graphics.Color.green
import android.graphics.Color.red
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.withStyledAttributes
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import kotlin.math.roundToInt

/**
 * Created by SSong-develop on 2023.08.13
 *
 * Gradient effected ImageView
 */
class GradientImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    enum class GradientDirection(val value: Int) {
        LEFT_TO_RIGHT(0),
        RIGHT_TO_LEFT(1),
        TOP_TO_BOTTOM(2),
        BOTTOM_TO_TOP(3),
        LEFT_TOP_TO_RIGHT_BOTTOM(4),
        LEFT_BOTTOM_TO_RIGHT_TOP(5),
        RIGHT_TOP_TO_LEFT_BOTTOM(6),
        RIGHT_BOTTOM_TO_LEFT_TOP(7)
    }

    private val gradientRectF = RectF()
    private val gradientPaint = Paint()

    private var startColor = BLACK
    private var endColor = WHITE
    private var gradientAlpha = 1.0f
    private var direction: GradientDirection = GradientDirection.LEFT_TO_RIGHT

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {
        context.withStyledAttributes(attrs, R.styleable.GradientImageView) {
            startColor = getColor(R.styleable.GradientImageView_gradient_start_color, startColor)
            endColor = getColor(R.styleable.GradientImageView_gradient_end_color, endColor)
            gradientAlpha = getFloat(R.styleable.GradientImageView_gradient_alpha, gradientAlpha)
            direction = getInt(
                R.styleable.GradientImageView_gradient_direction,
                GradientDirection.LEFT_TO_RIGHT.value
            ).toGradientDirection()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        initializeGradientRectF()
        initializePaint()
        canvas.drawRect(gradientRectF, gradientPaint)
    }

    private fun initializeGradientRectF() {
        gradientRectF.apply {
            top = 0f
            left = 0f
            right = width.toFloat()
            bottom = height.toFloat()
        }
    }

    private fun initializePaint() {
        gradientPaint.apply {
            shader = when (direction) {
                GradientDirection.LEFT_TO_RIGHT -> {
                    LinearGradient(
                        0f,
                        0f,
                        width.toFloat(),
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }

                GradientDirection.RIGHT_TO_LEFT -> {
                    LinearGradient(
                        width.toFloat(),
                        0f,
                        0f,
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }

                GradientDirection.TOP_TO_BOTTOM -> {
                    LinearGradient(
                        0f,
                        0f,
                        0f,
                        height.toFloat(),
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }

                GradientDirection.BOTTOM_TO_TOP -> {
                    LinearGradient(
                        0f,
                        height.toFloat(),
                        0f,
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }

                GradientDirection.LEFT_TOP_TO_RIGHT_BOTTOM -> {
                    LinearGradient(
                        0f,
                        0f,
                        width.toFloat(),
                        height.toFloat(),
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }

                GradientDirection.LEFT_BOTTOM_TO_RIGHT_TOP -> {
                    LinearGradient(
                        0f,
                        height.toFloat(),
                        width.toFloat(),
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }

                GradientDirection.RIGHT_TOP_TO_LEFT_BOTTOM -> {
                    LinearGradient(
                        width.toFloat(),
                        0f,
                        0f,
                        height.toFloat(),
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }

                GradientDirection.RIGHT_BOTTOM_TO_LEFT_TOP -> {
                    LinearGradient(
                        width.toFloat(),
                        height.toFloat(),
                        0f,
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
            }
            alpha = (gradientAlpha * 255).toInt()
        }
    }

    private fun updateBackground() {
        background =
            MaterialShapeDrawable(ShapeAppearanceModel().withCornerSize(0f)).apply {
                fillColor = ColorStateList.valueOf(WHITE)
            }
    }

    private fun Int.toGradientDirection(): GradientDirection =
        when (this) {
            0 -> GradientDirection.LEFT_TO_RIGHT
            1 -> GradientDirection.RIGHT_TO_LEFT
            2 -> GradientDirection.TOP_TO_BOTTOM
            3 -> GradientDirection.BOTTOM_TO_TOP
            4 -> GradientDirection.LEFT_TOP_TO_RIGHT_BOTTOM
            5 -> GradientDirection.LEFT_BOTTOM_TO_RIGHT_TOP
            6 -> GradientDirection.RIGHT_TOP_TO_LEFT_BOTTOM
            7 -> GradientDirection.RIGHT_BOTTOM_TO_LEFT_TOP
            else -> throw IllegalArgumentException("This value is not supported for GradientDirection : $this")
        }

    private fun Int.adjustAlpha(factor: Float): Int {
        val alpha = (alpha(this) * factor).roundToInt()
        val red = red(this)
        val green = green(this)
        val blue = blue(this)
        return argb(alpha, red, green, blue)
    }
}

