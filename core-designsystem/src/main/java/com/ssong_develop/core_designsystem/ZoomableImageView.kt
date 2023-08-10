package com.ssong_develop.core_designsystem

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView

class ZoomableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val gestureListener: GestureListener = GestureListener(
        onSingleTapBlock = {
            this.scaleX = 1.0f
            this.scaleY = 1.0f
        },
        onDoubleTapBlock = {

        }
    )

    private val zoomListener = ZoomListener() { scaleFactor ->
        scaleX = scaleFactor
        scaleY = scaleFactor
    }

    private val scaleGestureDetector: ScaleGestureDetector =
        ScaleGestureDetector(context, zoomListener)

    private val gestureDetector: GestureDetector =
        GestureDetector(context, gestureListener)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return event?.let {
            if (it.pointerCount == 2) {
                scaleGestureDetector.onTouchEvent(it)
            } else {
                gestureDetector.onTouchEvent(it)
            }
            true
        } ?: false
    }
}

internal class ZoomListener(
    private val callback: (Float) -> Unit
) : ScaleGestureDetector.SimpleOnScaleGestureListener() {

    private var factor: Float = 1.0f

    private var isScale: Boolean = false

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        factor *= detector.scaleFactor

        factor = 0.1f.coerceAtLeast(factor.coerceAtMost(10.0f))
        isScale != isScale
        callback.invoke(factor)
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        return super.onScaleBegin(detector)
    }

    override fun onScaleEnd(detector: ScaleGestureDetector) {
        super.onScaleEnd(detector)
    }
}

internal class GestureListener(
    private val onSingleTapBlock: () -> Unit,
    private val onDoubleTapBlock: () -> Unit
) : GestureDetector.SimpleOnGestureListener() {
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        onSingleTapBlock.invoke()
        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        onDoubleTapBlock.invoke()
        return true
    }
}