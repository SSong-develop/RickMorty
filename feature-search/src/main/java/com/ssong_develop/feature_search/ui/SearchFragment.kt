package com.ssong_develop.feature_search.ui

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
import androidx.paging.LoadState
import com.ssong_develop.core_common.toast
import com.ssong_develop.core_model.Characters
import com.ssong_develop.feature_search.R
import com.ssong_develop.feature_search.SearchItemClickDelegate
import com.ssong_develop.feature_search.SearchUiEvent
import com.ssong_develop.feature_search.SearchViewModel
import com.ssong_develop.feature_search.adapter.SearchResultPagingAdapter
import com.ssong_develop.feature_search.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment(), SearchItemClickDelegate {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var searchResultPagingAdapter: SearchResultPagingAdapter

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDataBinding()
        initAdapter()
        initRecyclerView()
        initObserve()
    }

    private fun initDataBinding() {
        with(binding) {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun initAdapter() {
        searchResultPagingAdapter = SearchResultPagingAdapter(this)
    }

    private fun initRecyclerView() {
        binding.rvSearchResult.adapter = searchResultPagingAdapter
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.searchResultStream.collectLatest { pagingData ->
                        searchResultPagingAdapter.submitData(pagingData)
                    }
                }

                launch {
                    searchResultPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                        when (loadStates.refresh) {
                            is LoadState.NotLoading -> {
                                viewModel.run {
                                    updateLoadingState(false)
                                    updateErrorState(false)
                                }
                            }
                            LoadState.Loading -> {
                                viewModel.run {
                                    updateLoadingState(true)
                                    updateErrorState(false)
                                }
                            }
                            is LoadState.Error -> {
                                viewModel.run {
                                    updateLoadingState(false)
                                    updateErrorState(true)
                                }
                            }
                        }
                    }
                }

                launch {
                    viewModel.searchUiEventBus.collectLatest { event ->
                        when (event) {
                            is SearchUiEvent.ShowToast -> requireContext().toast(
                                event.message
                            )
                            SearchUiEvent.Retry -> searchResultPagingAdapter.retry()
                            SearchUiEvent.Refresh -> searchResultPagingAdapter.refresh()
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    override fun onItemClick(characters: Characters) {}
}