package com.ssong_develop.feature_favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssong_develop.core_common.AutoClearedValue
import com.ssong_develop.core_model.RickMortyCharacter
import com.ssong_develop.feature_favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
internal class FavoriteFragment : Fragment() {
    private val viewModel: FavoriteViewModel by viewModels()

    private var binding: FragmentFavoriteBinding by AutoClearedValue(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater,container, false)
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
                                configHasFavoriteCharacterView(state.favoriteCharacter)
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

    private fun configHasFavoriteCharacterView(favoriteCharacter: RickMortyCharacter) {
        binding.hasFavCharacterView.tvFavCharacterInfo.text = favoriteCharacter.toString()
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