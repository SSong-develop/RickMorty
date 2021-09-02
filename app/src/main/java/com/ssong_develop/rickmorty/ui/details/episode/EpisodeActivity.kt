package com.ssong_develop.rickmorty.ui.details.episode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityEpisodeBinding

class EpisodeActivity : AppCompatActivity() {

    private val binding : ActivityEpisodeBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_episode)
    }

    private val viewModel : EpisodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
    }
}