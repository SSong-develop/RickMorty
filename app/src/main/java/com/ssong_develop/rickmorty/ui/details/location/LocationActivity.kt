package com.ssong_develop.rickmorty.ui.details.location

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity() {

    private val binding : ActivityLocationBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_location)
    }

    private val viewModel : LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
    }
}