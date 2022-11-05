package com.ssong_develop.feature_character.character

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
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.ConcatAdapter
import com.ssong_develop.core_model.Characters
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.character.adapters.CharacterPagingAdapter
import com.ssong_develop.feature_character.character.adapters.FooterAdapter
import com.ssong_develop.feature_character.character.adapters.FooterLoadStateAdapter
import com.ssong_develop.feature_character.character.viewholders.character.ItemClickDelegate
import com.ssong_develop.feature_character.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            lifecycleOwner = this@CharacterFragment
            vm = viewModel
        }
        initAdapter()
        initRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.pagingCharacterFlow.collectLatest { pagingData ->
                        pagingAdapter.submitData(pagingData)
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
                    viewModel.uiEventState.collectLatest { uiEvent ->
                        when(uiEvent) {
                            CharacterViewModel.CharacterUiEvent.Retry -> pagingAdapter.retry()
                            CharacterViewModel.CharacterUiEvent.Refresh -> pagingAdapter.refresh()
                        }
                    }
                }
            }
        }
    }

    override fun onItemClick(view: View, characters: Characters) {
        val bundle = Bundle()
        bundle.putParcelable("character",characters)
        findNavController().navigate(R.id.action_characterFragment_to_characterDetailFragment,bundle)
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
}