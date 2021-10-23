package com.ssong_develop.rickmorty.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.RickMortyApp.Companion.versionCheckUtils
import com.ssong_develop.rickmorty.databinding.ActivityCharacterDetailBinding
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.entities.getEpisodeNumbers
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.ui.adapters.CharacterEpisodeListAdapter
import com.ssong_develop.rickmorty.ui.viewholders.CharacterEpisodeListViewHolder
import com.ssong_develop.rickmorty.utils.observeOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterDetailActivity : AppCompatActivity(), CharacterEpisodeListViewHolder.Delegate {

    private val binding: ActivityCharacterDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_character_detail)
    }

    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.postCharacter(intent.getParcelableExtra(CHARACTER)!!)
        with(binding) {
            vm = viewModel
            activity = this@CharacterDetailActivity
        }

        binding.episodeList.apply {
            adapter = CharacterEpisodeListAdapter(this@CharacterDetailActivity)
        }
        viewModel.toast.observe(this) {
            toast(it)
        }
    }

    companion object {
        private const val CHARACTER = "CHARACTER"

        fun startActivity(activity: Activity?, view: View, character: Characters) {
            if (activity != null) {
                val intent = Intent(activity, CharacterDetailActivity::class.java).apply {
                    putExtra(CHARACTER, character)
                }
                if (versionCheckUtils.checkIsMaterialVersion()) {
                    ViewCompat.getTransitionName(view)?.let {
                        val options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, it)
                        activity.startActivity(intent, options.toBundle())
                    }
                } else {
                    activity.startActivity(intent)
                }
            }
        }
    }

    override fun onItemClick(view: View, episode: Episode) {
        toast("hello")
    }
}