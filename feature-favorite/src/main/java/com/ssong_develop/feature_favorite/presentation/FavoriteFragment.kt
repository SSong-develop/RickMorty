package com.ssong_develop.feature_favorite.presentation

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.ssong_develop.core_common.AutoClearedValue
import com.ssong_develop.core_common.extension.convertDateToString
import com.ssong_develop.core_common.extension.convertStringToDate
import com.ssong_develop.core_common.extension.dpToPx
import com.ssong_develop.feature_favorite.R
import com.ssong_develop.feature_favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

@ExperimentalCoroutinesApi
@AndroidEntryPoint
internal class FavoriteFragment : Fragment() {
    private val viewModel: FavoriteViewModel by viewModels()

    private var binding: FragmentFavoriteBinding by AutoClearedValue(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collectLatest { state ->
                        when (state) {
                            is UiState.HasFavoriteCharacter -> {
                                showHasFavoriteCharacterView()
                                binding.hasFavCharacterView.calendarFavEpisodeAirDate.initFavCharacter(state.favoriteCharacter)
                                binding.hasFavCharacterView.calendarFavEpisodeAirDate.submitEpisodeAirDates(state.episodeAirDates)
                                state.episodeAirDates.forEach {
                                    binding.hasFavCharacterView.flexBoxChip.addView(
                                        Chip(requireContext()).apply {
                                            text = it.convertDateToString()
                                            gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
                                            layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                                                marginStart = requireContext().dpToPx(4)
                                                marginEnd = requireContext().dpToPx(4)
                                            }
                                            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                                        }
                                    )
                                }
                            }

                            UiState.Loading -> {
                                showLoadingView()
                            }

                            UiState.NoFavoriteCharacter -> {
                                showNoFavoriteCharacterView()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showHasFavoriteCharacterView() {
        binding.apply {
            noFavCharacterView.root.isVisible = false
            loadingView.root.isVisible = false
            hasFavCharacterView.root.isVisible = true
        }
    }

    private fun showNoFavoriteCharacterView() {
        binding.apply {
            noFavCharacterView.root.isVisible = true
            loadingView.root.isVisible = false
            hasFavCharacterView.root.isVisible = false
        }
    }

    private fun showLoadingView() {
        binding.apply {
            noFavCharacterView.root.isVisible = false
            loadingView.root.isVisible = true
            hasFavCharacterView.root.isVisible = false
        }
    }
}