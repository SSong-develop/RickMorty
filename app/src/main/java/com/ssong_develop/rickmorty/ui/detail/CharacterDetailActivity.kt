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
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityCharacterDetailBinding
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.ui.adapters.CharacterEpisodeAdapter
import com.ssong_develop.rickmorty.ui.viewholders.CharacterEpisodeViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterDetailActivity : AppCompatActivity(), CharacterEpisodeViewHolder.Delegate {

    private val binding: ActivityCharacterDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_character_detail)
    }

    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@CharacterDetailActivity
            activity = this@CharacterDetailActivity
            vm = viewModel.apply {
                if (Build.VERSION.SDK_INT >= 33) {
                    postCharacter(intent.getParcelableExtra(CHARACTER, Characters::class.java) ?: return)
                } else {
                    postCharacter(intent.getParcelableExtra(CHARACTER) ?: return)
                }
            }
            episodeAdapter = CharacterEpisodeAdapter(this@CharacterDetailActivity)
        }
    }

    override fun onItemClick(view: View, episode: Episode) {
        Timber.d("todo!")
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