package com.ssong_develop.feature_search

import com.ssong_develop.core_model.Characters

interface SearchItemClickDelegate {
    fun onItemClick(
        characters: Characters
    )
}