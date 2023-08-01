package com.ssong_develop.core_designsystem

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

class CharacterStatusTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        val drawable = ContextCompat.getDrawable(context, R.drawable.sample)
            ?: throw IllegalStateException("문제임 문제")

        setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }
}