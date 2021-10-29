package com.ssong_develop.rickmorty.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssong_develop.rickmorty.databinding.ActivityCharacterDetailBinding

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}