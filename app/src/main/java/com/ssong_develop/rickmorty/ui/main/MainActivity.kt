package com.ssong_develop.rickmorty.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityMainBinding
import com.ssong_develop.rickmorty.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.characters.observe(this) {
            toast(it.toString())
        }

        viewModel.locations.observe(this) {
            toast(it.toString())
        }

        viewModel.episodes.observe(this) {
            toast(it.toString())
        }
    }
}