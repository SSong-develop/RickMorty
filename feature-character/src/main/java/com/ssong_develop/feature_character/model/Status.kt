package com.ssong_develop.feature_character.model

import androidx.annotation.ColorRes
import com.ssong_develop.feature_character.R

enum class Status(val status: String, @ColorRes val colorString: Int) {
    ALIVE("Alive", R.color.alive), DEAD("Dead", R.color.dead), UNKNOWN("unknown", R.color.gray);

    companion object {
        fun color(status: String): Int? = values().find { it.status == status }?.colorString
    }
}
