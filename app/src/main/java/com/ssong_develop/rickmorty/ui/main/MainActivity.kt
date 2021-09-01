package com.ssong_develop.rickmorty.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityMainBinding
import com.ssong_develop.rickmorty.entities.Theme
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.ui.adapters.ThemeListAdapter
import com.ssong_develop.rickmorty.ui.viewholders.ThemeListViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),ThemeListViewHolder.Delegate {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapterMainTheme : ThemeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeUI()

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

    private fun initializeUI() {
        adapterMainTheme = ThemeListAdapter(this)
        adapterMainTheme.submitList(viewModel.dummy)
        binding.apply {
            pagerMain.adapter = adapterMainTheme
        }
    }

    override fun onItemClick(view: View, theme: Theme) {
        toast(theme.toString())
    }
}