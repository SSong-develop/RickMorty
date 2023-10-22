package com.ssong_develop.feature_character.presentation.character

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.ssong_develop.core_common.extension.setupSnackbarManager
import com.ssong_develop.core_common.helper.ViewPager2AutoScrollHelper
import com.ssong_develop.core_common.manager.SnackbarMessageManager
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.databinding.FragmentCharacterBinding
import com.ssong_develop.feature_character.model.mapper.asUiModel
import com.ssong_develop.feature_character.presentation.character.adapters.CharacterPagingAdapter
import com.ssong_develop.feature_character.presentation.character.adapters.FooterLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
internal class CharacterFragment : Fragment() {
    private val viewModel: CharacterViewModel by viewModels()

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = requireNotNull(_binding) { "[${this.javaClass.kotlin.simpleName}] binding is null" }

    private var _characterPagingAdapter: CharacterPagingAdapter? = null
    private val characterPagingAdapter get() = requireNotNull(_characterPagingAdapter) { "[${this.javaClass.kotlin.simpleName}] _characterPagingAdapter is null" }

    private var _footerLoadStateAdapter: FooterLoadStateAdapter? = null
    private val footerLoadStateAdapter get() = requireNotNull(_footerLoadStateAdapter) { "[${this.javaClass.kotlin.simpleName}] _footerLoadStateAdapter is null" }

    private lateinit var advertiseAutoScrollHelper: ViewPager2AutoScrollHelper

    @Inject
    lateinit var snackbarMessageManager: SnackbarMessageManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character, container, false)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupSnackbarManager(snackbarMessageManager, binding.snackBar)
        initTransition()
        initBinding()
        initView()
        initListener()
        initObserver()
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

    private fun initView() {
        _characterPagingAdapter = CharacterPagingAdapter { transitionModel ->
            val bundle = Bundle().apply { putParcelable(CHARACTER_KEY, transitionModel.character) }
            val extras = FragmentNavigatorExtras(*transitionModel.viewAndTransitionNameList)
            findNavController().navigate(
                R.id.action_characterFragment_to_characterDetailFragment,
                args = bundle,
                navOptions = null,
                navigatorExtras = extras
            )
        }
        _footerLoadStateAdapter = FooterLoadStateAdapter()

        characterPagingAdapter.withLoadStateFooter(
            footer = footerLoadStateAdapter
        )

        with(binding) {
            rvCharacter.adapter = characterPagingAdapter
        }
    }

    private fun initListener() {
        with(binding) {
            swipeRefresh.setOnRefreshListener {
                characterPagingAdapter.refresh()
                swipeRefresh.isRefreshing = false
            }

            viewSearchError.retryBtn.setOnClickListener {
                characterPagingAdapter.retry()
            }
        }
    }

    private fun initObserver() {
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
                            is LoadState.NotLoading -> viewModel.updateUiState(UiState.Characters(viewModel.favoriteCharacterState.value?.asUiModel()))
                            LoadState.Loading -> viewModel.updateUiState(UiState.Loading)
                            is LoadState.Error -> viewModel.updateUiState(UiState.Error)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val CHARACTER_KEY = "character"
    }
}