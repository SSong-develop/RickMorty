package com.ssong_develop.core_common.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import com.ssong_develop.core_common.extension.dpToPx

class HorizontalSpacer(
    context: Context
) : View(context) {

    init {
        layoutParams = LinearLayout.LayoutParams(context.dpToPx(12f), MATCH_PARENT)
    }
}