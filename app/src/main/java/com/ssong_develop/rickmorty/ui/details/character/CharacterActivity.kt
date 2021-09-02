package com.ssong_develop.rickmorty.ui.details.character

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityCharacterBinding
import com.ssong_develop.rickmorty.utils.AutoClearedValue

class CharacterActivity : AppCompatActivity() {

    private val binding : ActivityCharacterBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_character)
    }

    private val viewModel : CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
    }
}