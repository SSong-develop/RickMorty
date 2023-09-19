package com.ssong_develop.core_designsystem

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

/**
 * 캐릭터의 상태에 따라(죽었는지, 살았는지, 모르는지) 표시 해주는 텍스트 뷰
 */
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