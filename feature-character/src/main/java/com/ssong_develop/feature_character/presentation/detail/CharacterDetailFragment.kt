package com.ssong_develop.feature_character.presentation.detail

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.ConcatAdapter
import com.bumptech.glide.Glide
import com.ssong_develop.core_common.AutoClearedValue
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.databinding.FragmentCharacterDetailBinding
import com.ssong_develop.feature_character.presentation.character.adapters.FooterAdapter
import com.ssong_develop.feature_character.presentation.detail.adapters.CharacterEpisodeAdapter
import com.ssong_develop.feature_character.presentation.detail.adapters.EpisodeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
internal class CharacterDetailFragment : Fragment() {
    private val viewModel: CharacterDetailViewModel by viewModels()

    private var binding: FragmentCharacterDetailBinding by AutoClearedValue(this)
    private var characterEpisodeAdapter: CharacterEpisodeAdapter by AutoClearedValue(this)
    private var episodeAdapter: EpisodeAdapter by AutoClearedValue(this)
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
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
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
        episodeAdapter = EpisodeAdapter { _ ->

        }
        footerAdapter = FooterAdapter(requireContext())
        concatAdapter = ConcatAdapter().apply {
            addAdapter(episodeAdapter)
            addAdapter(footerAdapter)
        }

        binding.apply {
            episodeList.adapter = concatAdapter
        }
    }

    private fun initListener() {
        binding.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collectLatest { uiState ->
                        episodeAdapter.submitList(uiState.characterEpisode)
                        if (uiState.characterEpisode.isNotEmpty()) {
                            concatAdapter.removeAdapter(footerAdapter)
                        }
                        if (uiState.character != null) {
                            val dominantColor =
                                uiState.character.dominantColor ?: ContextCompat.getColor(
                                    requireContext(),
                                    R.color.app_bar_color
                                )
                            binding.header.setBackgroundColor(dominantColor)
                            Glide.with(requireContext())
                                .load(uiState.character.image)
                                .into(binding.ivCharacter)
                        }
                    }
                }

                launch {
                    viewModel.isFavoriteCharacterState.collectLatest { isFavCharacterState ->
                        val favIconDrawable = if (isFavCharacterState) {
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_filled_favorite
                            )
                        } else {
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_empty_favorite
                            )
                        }

                        val color = viewModel.uiState.value.character?.dominantColor
                            ?: ContextCompat.getColor(requireContext(), R.color.app_bar_color)

                        favIconDrawable?.mutate()
                        favIconDrawable?.clearColorFilter()
                        favIconDrawable?.colorFilter =
                            PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                        binding.ivFav.setImageDrawable(favIconDrawable)
                    }
                }
            }
        }
    }
}