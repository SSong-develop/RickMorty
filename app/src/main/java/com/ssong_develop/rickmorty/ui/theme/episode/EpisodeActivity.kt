package com.ssong_develop.rickmorty.ui.theme.episode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.RickMortyApp.Companion.versionCheckUtils
import com.ssong_develop.rickmorty.databinding.ActivityEpisodeBinding
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.ui.adapters.EpisodeListAdapter
import com.ssong_develop.rickmorty.ui.theme.character.CharacterActivity
import com.ssong_develop.rickmorty.ui.viewholders.EpisodeListViewHolder
import com.ssong_develop.rickmorty.utils.observeOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EpisodeActivity : AppCompatActivity() , EpisodeListViewHolder.Delegate {

    private val binding: ActivityEpisodeBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_episode)
    }

    private val viewModel: EpisodeViewModel by viewModels()

    private lateinit var episodeAdapter : EpisodeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding){
            lifecycleOwner = this@EpisodeActivity
            vm = viewModel
        }
        initializeUI()
    }

    private fun initializeUI() {
        episodeAdapter = EpisodeListAdapter(this)
        binding.rvEpisode.apply {
            adapter = episodeAdapter
            layoutManager = GridLayoutManager(this@EpisodeActivity, SPAN_COUNT)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = binding.rvEpisode.layoutManager

                    val lastVisibleItem =
                        (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()

                    if (layoutManager.itemCount <= lastVisibleItem + SPAN_COUNT) {
                        viewModel.morePage()
                    }
                }
            })
        }
    }

    override fun onItemClick(view: View, episode: Episode) {
        toast("hello!")
    }

    companion object {
        private const val SPAN_COUNT = 2

        fun startActivityTransition(activity: Activity?, view: View) {
            if (activity != null) {
                val intent = Intent(activity, EpisodeActivity::class.java)
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
}