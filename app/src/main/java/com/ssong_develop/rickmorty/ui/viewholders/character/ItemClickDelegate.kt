package com.ssong_develop.rickmorty.ui.viewholders.character

import android.view.View
import com.ssong_develop.core_model.Characters

interface ItemClickDelegate {
    fun onItemClick(view: View, characters: Characters)
}