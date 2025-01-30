package com.ssong_develop.core_designsystem

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewPropertyAnimator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.card.MaterialCardView

class ElasticView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialCardView(context, attrs) {

    private var isElasticAnimating = false
    private var _isActionUpPerformed = false

    // 외부에서 속성 접근이 가능하도록 하므로 public
    var flexibility = 5f
        set(value) {
            if (value !in 1f..10f) {
                throw IllegalArgumentException("Flexibility must be between [1f..10f].")
            }
            field = value
            invalidate()
        }

    init {
        isClickable = true
        init(attrs)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        processTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    private fun processTouchEvent(event: MotionEvent) {
        val verticalRotation = calculateRotation((event.x * flexibility * 2) / width)
        val horizontalRotation = -calculateRotation((event.y * flexibility * 2) / height)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                animator {
                    rotationY(verticalRotation)
                    rotationX(horizontalRotation)
                    duration = SHORT_DURATION
                    withStartAction {
                        _isActionUpPerformed = false
                        isElasticAnimating = true
                    }
                    withEndAction {
                        if (_isActionUpPerformed) {
                            animateToOriginalPosition()
                        } else {
                            isElasticAnimating = false
                        }
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                rotationY = verticalRotation
                rotationX = horizontalRotation
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                _isActionUpPerformed = true
                if (!isElasticAnimating) {
                    animateToOriginalPosition()
                }
            }
        }
    }

    private fun init(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.ElasticView).apply {
            if (hasValue(R.styleable.ElasticView_flexibility)) {
                flexibility = getFloat(R.styleable.ElasticView_flexibility, flexibility)
            }
            recycle()
        }
    }

    private fun animator(body: ViewPropertyAnimator.() -> Unit) {
        animate().apply {
            interpolator = FastOutSlowInInterpolator()
            body()
            start()
        }
    }

    private fun animateToOriginalPosition() {
        animator {
            rotationX(0f)
            rotationY(0f)
            duration = DEFAULT_DURATION
        }
    }

    private fun calculateRotation(value: Float): Float {
        var tempValue = when {
            value < 0 -> 1f
            value > flexibility * 2 -> flexibility * 2
            else -> value
        }
        tempValue -= flexibility
        return tempValue
    }

    companion object {
        private const val DEFAULT_DURATION = 200L
        private const val SHORT_DURATION = 100L
    }
}