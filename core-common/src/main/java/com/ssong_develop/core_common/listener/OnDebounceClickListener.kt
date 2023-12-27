package com.ssong_develop.core_common.listener

import android.view.View

typealias OnClickListener = (View) -> Unit

class OnDebounceClickListener(private val listener: OnClickListener) : View.OnClickListener {
    override fun onClick(view: View?) {
        val now = System.currentTimeMillis()
        if (now < lastTime + INTERVAL) return
        lastTime = now
        view?.run(listener)
    }

    companion object {
        private const val INTERVAL: Long = 200L
        private var lastTime: Long = 0
    }
}
