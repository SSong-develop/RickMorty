package com.ssong_develop.feature_character.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.ssong_develop.core_common.toast
import com.ssong_develop.core_model.Episode
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.character.adapters.FooterAdapter
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
    private val viewModel: CharacterDetailViewModel by viewModels()
    private lateinit var binding: FragmentCharacterDetailBinding
    private lateinit var episodeAdapter: CharacterEpisodeAdapter
    private lateinit var footerAdapter: FooterAdapter
    private val concatAdapter = ConcatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_character_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
        initDataBinding()
        initAdapter()
        initRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.characterDetailUiEventBus.collectLatest { uiEvent ->
                        when (uiEvent) {
                            CharacterDetailViewModel.CharacterDetailUiEvent.Back -> navigateToBackStack()
                            is CharacterDetailViewModel.CharacterDetailUiEvent.ShowToast -> {
                                requireContext().toast(uiEvent.message)
                            }
                        }
                    }
                }

                launch {
                    viewModel.uiState.collectLatest { uiState ->
                        episodeAdapter.submitEpisodes(uiState.characterEpisode)
                        if (uiState.characterEpisode.isNotEmpty()) {
                            concatAdapter.removeAdapter(footerAdapter)
                        }
                    }
                }
            }
        }
    }

    override fun onItemClick(view: View, episode: Episode) {}

    private fun initDataBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
    }

    private fun initAdapter() {
        episodeAdapter = CharacterEpisodeAdapter(this)
        footerAdapter = FooterAdapter(requireContext())
        concatAdapter.apply {
            addAdapter(episodeAdapter)
            addAdapter(footerAdapter)
        }
    }

    private fun initRecyclerView() {
        binding.episodeList.apply {
            this.adapter = concatAdapter
        }
    }

    private fun navigateToBackStack() {
        findNavController().popBackStack()
    }
}