package com.ssong_develop.feature_character.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.character.viewholders.character.ItemClickDelegate
import com.ssong_develop.feature_character.databinding.FragmentCharacterDetailBinding
import com.ssong_develop.feature_character.detail.adapters.CharacterEpisodeAdapter
import com.ssong_develop.feature_character.detail.viewholders.CharacterEpisodeViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterDetailFragment : Fragment(), CharacterEpisodeViewHolder.Delegate {

    private lateinit var binding: FragmentCharacterDetailBinding

    private val viewModel: CharacterDetailViewModel by viewModels()

    private lateinit var episodeAdapter: CharacterEpisodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_character_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            lifecycleOwner = this@CharacterDetailFragment
            vm = viewModel
        }
        initAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
        TODO("Not yet implemented")
    }

    private fun initAdapter() {
        episodeAdapter = CharacterEpisodeAdapter(this)
        binding.episodeList.apply {
            this.adapter = episodeAdapter
        }
    }
}