package com.ssong_develop.feature_character.presentation.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
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
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.ConcatAdapter
import com.ssong_develop.core_common.AutoClearedValue
import com.ssong_develop.core_common.animateScaleUp
import com.ssong_develop.core_common.toast
import com.ssong_develop.core_model.RickMortyCharacterEpisode
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.databinding.FragmentCharacterDetailBinding
import com.ssong_develop.feature_character.presentation.character.adapters.FooterAdapter
import com.ssong_develop.feature_character.presentation.detail.adapters.CharacterEpisodeAdapter
import com.ssong_develop.feature_character.presentation.detail.viewholders.CharacterEpisodeViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
internal class CharacterDetailFragment : Fragment() {
    private val viewModel: CharacterDetailViewModel by viewModels()

    private var binding: FragmentCharacterDetailBinding by AutoClearedValue(this)
    private var characterEpisodeAdapter: CharacterEpisodeAdapter by AutoClearedValue(this)
    private var footerAdapter: FooterAdapter by AutoClearedValue(this)
    private var concatAdapter: ConcatAdapter by AutoClearedValue(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_character_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inflateTransition()
        initBinding()
        initView()
        initListener()
        initObserver()
    }

    private fun inflateTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
    }

    private fun initView() {
        characterEpisodeAdapter = CharacterEpisodeAdapter { _ ->
            /** no - op **/
        }
        footerAdapter = FooterAdapter(requireContext())
        concatAdapter = ConcatAdapter().apply {
            addAdapter(characterEpisodeAdapter)
            addAdapter(footerAdapter)
        }

        with(binding) {
            episodeList.adapter = concatAdapter
        }
    }

    private fun initListener() {
        binding.apply {
            ivBack.setOnClickListener {
                navigateToBackStack()
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collectLatest { uiState ->
                        characterEpisodeAdapter.submitEpisodes(uiState.characterEpisode)
                        if (uiState.characterEpisode.isNotEmpty()) {
                            concatAdapter.removeAdapter(footerAdapter)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToBackStack() {
        findNavController().popBackStack()
    }
}