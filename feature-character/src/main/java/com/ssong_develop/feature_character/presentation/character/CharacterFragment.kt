package com.ssong_develop.feature_character.presentation.character

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.databinding.FragmentCharacterBinding
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel
import com.ssong_develop.feature_character.model.asUiModel
import com.ssong_develop.feature_character.presentation.character.adapters.CharacterPagingAdapter
import com.ssong_develop.feature_character.presentation.character.adapters.FooterLoadStateAdapter
import com.ssong_develop.feature_character.presentation.character.viewholders.ItemClickDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterFragment : Fragment(), ItemClickDelegate {
    private val viewModel: CharacterViewModel by viewModels()

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = requireNotNull(_binding) { "[${TAG}] binding is null" }

    private var _characterPagingAdapter: CharacterPagingAdapter? = null
    private val characterPagingAdapter get() = requireNotNull(_characterPagingAdapter) { "[${TAG}] _characterPagingAdapter is null" }

    private var _footerLoadStateAdapter: FooterLoadStateAdapter? = null
    private val footerLoadStateAdapter get() = requireNotNull(_footerLoadStateAdapter) { "[${TAG}] _footerLoadStateAdapter is null"}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character, container, false)
        inflateTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initTransition()
        initBinding()
        initListener()
        initAdapter()
        initRecyclerView()
        initObserve()
    }

    override fun onItemClick(
        characterImageView: View,
        characterNameView: View,
        characterImageTransitionName: String,
        characterNameTransitionName: String,
        characters: RickMortyCharacterUiModel
    ) {
        val bundle = Bundle()
        bundle.putParcelable(CHARACTER_KEY, characters)
        val extras = FragmentNavigatorExtras(
            characterImageView to characterImageTransitionName,
            characterNameView to characterNameTransitionName
        )
        findNavController().navigate(
            R.id.action_characterFragment_to_characterDetailFragment,
            args = bundle,
            navOptions = null,
            navigatorExtras = extras
        )
    }

    private fun inflateTransition() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
    }

    private fun initTransition() {
        postponeEnterTransition()
        (binding.root.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
    }

    private fun initListener() {
        with(binding) {
            ivSearch.setOnClickListener {
                navigateToSearch()
            }

            ivFav.setOnClickListener {
                navigateToFavoriteCharacter()
            }

            swipeRefresh.setOnRefreshListener {
                characterPagingAdapter.refresh()
            }

            viewSearchError.retryBtn.setOnClickListener {
                characterPagingAdapter.retry()
            }
        }
    }

    private fun initAdapter() {
        _characterPagingAdapter = CharacterPagingAdapter(this)
        _footerLoadStateAdapter = FooterLoadStateAdapter()
        characterPagingAdapter.withLoadStateFooter(
            footer = footerLoadStateAdapter
        )
    }

    private fun initRecyclerView() {
        binding.rvCharacter.adapter = characterPagingAdapter
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.networkRickMortyCharacterPagingStream.collectLatest { characterPagingData ->
                        characterPagingAdapter.submitData(characterPagingData)
                    }
                }

                launch {
                    characterPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                        when (loadStates.refresh) {
                            is LoadState.NotLoading -> {
                                viewModel.updateUiState(CharacterUiState.Characters())
                            }
                            LoadState.Loading -> {
                                viewModel.updateUiState(CharacterUiState.Loading)
                            }
                            is LoadState.Error -> {
                                viewModel.updateUiState(CharacterUiState.Error)
                            }
                        }
                    }
                }

                launch {
                    viewModel.favoriteCharacterState
                        .filterNotNull()
                        .collectLatest { favoriteCharacter ->
                            viewModel.updateFavoriteCharacter(favoriteCharacter = favoriteCharacter.asUiModel())
                        }
                }
            }
        }
    }

    private fun navigateToFavoriteCharacter() {
        if (viewModel.uiState.value is CharacterUiState.Characters) {
            (viewModel.uiState.value as CharacterUiState.Characters).favoriteCharacter?.let {
                val bundle = Bundle()
                bundle.putParcelable(CHARACTER_KEY, it)
                findNavController().navigate(
                    R.id.action_characterFragment_to_characterDetailFragment,
                    bundle
                )
            } ?: run {
                // No Favorite Character
            }
        }
    }

    private fun navigateToSearch() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(SEARCH_FRAGMENT_DEEP_LINK_URI.toUri())
            .build()
        findNavController().navigate(request)
    }

    companion object {
        private const val TAG = "CharacterFragment"
        private const val CHARACTER_KEY = "character"
        private const val SEARCH_FRAGMENT_DEEP_LINK_URI =
            "android-app://com.ssong-develop.com/rick_morty/search_fragment"
    }
}