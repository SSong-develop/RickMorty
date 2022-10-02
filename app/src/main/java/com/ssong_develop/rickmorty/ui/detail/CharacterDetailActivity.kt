package com.ssong_develop.rickmorty.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityCharacterDetailBinding
import com.ssong_develop.rickmorty.ui.adapters.CharacterEpisodeAdapter
import com.ssong_develop.rickmorty.ui.viewholders.CharacterEpisodeViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterDetailActivity : AppCompatActivity(), CharacterEpisodeViewHolder.Delegate {

    private val binding: ActivityCharacterDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_character_detail)
    }

    private val viewModel: CharacterDetailViewModel by viewModels()

    private lateinit var episodeAdapter: CharacterEpisodeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@CharacterDetailActivity
            activity = this@CharacterDetailActivity
            vm = viewModel.apply {
                if (Build.VERSION.SDK_INT >= 33) {
                    postCharacter(
                        intent.getParcelableExtra(CHARACTER, Characters::class.java) ?: return
                    )
                } else {
                    postCharacter(intent.getParcelableExtra(CHARACTER) ?: return)
                }
            }
        }
        initAdapter()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterEpisodesFlow.collectLatest { resources ->
                    when (resources.status) {
                        Resource.Status.SUCCESS -> {
                            binding.pbEpisodeLoading.visibility = View.GONE
                            episodeAdapter.submitEpisodes(resources.data ?: emptyList())
                        }
                        Resource.Status.ERROR -> {
                            binding.pbEpisodeLoading.visibility = View.VISIBLE
                        }
                        Resource.Status.LOADING -> {
                            binding.pbEpisodeLoading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onItemClick(view: View, episode: Episode) {
        Timber.d("todo!")
    }

    private fun initAdapter() {
        episodeAdapter = CharacterEpisodeAdapter(this)
        binding.episodeList.apply {
            this.adapter = episodeAdapter
        }
    }

    companion object {
        private const val CHARACTER = "CHARACTER"

        fun startActivity(activity: Activity?, view: View, character: Characters) {
            if (activity != null) {
                val intent = Intent(activity, CharacterDetailActivity::class.java).apply {
                    putExtra(CHARACTER, character)
                }
                ViewCompat.getTransitionName(view)?.let {
                    val options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, it)
                    activity.startActivity(intent, options.toBundle())
                }
            }
        }
    }
}