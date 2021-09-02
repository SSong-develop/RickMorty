package com.ssong_develop.rickmorty.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityMainBinding
import com.ssong_develop.rickmorty.entities.Theme
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.ui.adapters.ThemeListAdapter
import com.ssong_develop.rickmorty.ui.adapters.setPageTranslation
import com.ssong_develop.rickmorty.ui.details.character.CharacterActivity
import com.ssong_develop.rickmorty.ui.details.episode.EpisodeActivity
import com.ssong_develop.rickmorty.ui.details.location.LocationActivity
import com.ssong_develop.rickmorty.ui.viewholders.ThemeListViewHolder
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ThemeListViewHolder.Delegate {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapterMainTheme: ThemeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        initializeUI()
    }

    private fun initializeUI() {
        adapterMainTheme = ThemeListAdapter(this)
        with(binding.pagerMain) {
            adapter = adapterMainTheme
            adapterMainTheme.submitList(viewModel.getDefaultThemeList())
            setPageTranslation()
        }
    }

    override fun onItemClick(view: View, theme: Theme) {
        when (binding.pagerMain.currentItem) {
            0 -> {
                val intent = Intent(this,CharacterActivity::class.java)
                startActivity(intent)
            }
            1 -> {
                val intent = Intent(this,LocationActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                val intent = Intent(this,EpisodeActivity::class.java)
                startActivity(intent)
            }
            else -> {
                throw IllegalStateException("No such Position in here")
            }
        }
    }
}