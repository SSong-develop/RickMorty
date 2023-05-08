package com.ssong_develop.feature_character.character

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
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
import androidx.paging.map
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.character.adapters.CharacterPagingAdapter
import com.ssong_develop.feature_character.character.adapters.FooterLoadStateAdapter
import com.ssong_develop.feature_character.character.viewholders.ItemClickDelegate
import com.ssong_develop.feature_character.databinding.FragmentCharacterBinding
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel
import com.ssong_develop.feature_character.model.asUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterFragment : Fragment(), ItemClickDelegate {
    private val viewModel: CharacterViewModel by viewModels()

    private lateinit var binding: FragmentCharacterBinding
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private lateinit var loadStateAdapter: FooterLoadStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character, container, false)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        (binding.root.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }
        initDataBinding()
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

    private fun initDataBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
    }

    private fun initAdapter() {
        pagingAdapter = CharacterPagingAdapter(this)
        loadStateAdapter = FooterLoadStateAdapter()
        pagingAdapter.withLoadStateFooter(
            footer = loadStateAdapter
        )
    }

    private fun initRecyclerView() {
        binding.rvCharacter.adapter = pagingAdapter
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                launch {
//                    viewModel.characterStream.collectLatest { uiModelPagingData ->
//                        pagingAdapter.submitData(uiModelPagingData)
//                    }
//                }

                launch {
                    viewModel.tempStream.collectLatest { uiModelPagingData ->
                        pagingAdapter.submitData(uiModelPagingData)
                    }
                }
                launch {
                    pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                        when (loadStates.refresh) {
                            is LoadState.NotLoading -> {
                                viewModel.updateLoadingState(false)
                                viewModel.updateErrorState(false)
                            }
                            LoadState.Loading -> {
                                viewModel.updateLoadingState(true)
                                viewModel.updateErrorState(false)
                            }
                            is LoadState.Error -> {
                                viewModel.updateLoadingState(false)
                                viewModel.updateErrorState(true)
                            }
                        }
                    }
                }
                launch {
                    viewModel.characterUiEventBus.collectLatest { uiEvent ->
                        when (uiEvent) {
                            CharacterEvent.Retry -> pagingAdapter.retry()
                            CharacterEvent.Refresh -> pagingAdapter.refresh()
                            CharacterEvent.Favorite -> navigateToFavoriteCharacter()
                            CharacterEvent.Search -> navigateToSearch()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToFavoriteCharacter() {
        val bundle = Bundle()
        bundle.putParcelable(CHARACTER_KEY, viewModel.favoriteCharacterState.value?.asUiModel())
        findNavController().navigate(
            R.id.action_characterFragment_to_characterDetailFragment,
            bundle
        )
    }

    private fun navigateToSearch() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(SEARCH_FRAGMENT_DEEP_LINK_URI.toUri())
            .build()
        findNavController().navigate(request)
    }

    companion object {
        private const val CHARACTER_KEY = "character"
        private const val SEARCH_FRAGMENT_DEEP_LINK_URI =
            "android-app://com.ssong-develop.com/rick_morty/search_fragment"
    }
}