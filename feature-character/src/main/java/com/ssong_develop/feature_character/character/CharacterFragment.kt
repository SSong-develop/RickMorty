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
import androidx.recyclerview.widget.ConcatAdapter
import com.ssong_develop.core_model.Characters
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.character.adapters.CharacterPagingAdapter
import com.ssong_develop.feature_character.character.adapters.FooterAdapter
import com.ssong_develop.feature_character.character.viewholders.character.ItemClickDelegate
import com.ssong_develop.feature_character.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterFragment : Fragment(), ItemClickDelegate {

    private lateinit var binding: FragmentCharacterBinding

    private val viewModel: CharacterViewModel by viewModels()

    private lateinit var pagingAdapter: CharacterPagingAdapter

    private lateinit var footerAdapter: FooterAdapter

    private val concatAdapter = ConcatAdapter()

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
                    viewModel.pagingCharacterFlow.collectLatest {
                        pagingAdapter.submitData(it)
                    }
                }
                launch {
                    pagingAdapter.loadStateFlow.collectLatest {
                        when (it.source.append) {
                            is LoadState.Loading -> {
                                concatAdapter.addAdapter(footerAdapter)
                            }
                            else -> {
                                concatAdapter.removeAdapter(footerAdapter)
                            }
                        }
                    }
                }
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            pagingAdapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onItemClick(view: View, characters: Characters) {
        val bundle = Bundle()
        bundle.putParcelable("character",characters)
        findNavController().navigate(R.id.action_characterFragment_to_characterDetailFragment,bundle)
    }

    private fun initAdapter() {
        pagingAdapter = CharacterPagingAdapter(this)
        footerAdapter = FooterAdapter(requireContext())
        concatAdapter.addAdapter(pagingAdapter)
    }

    private fun initRecyclerView() {
        binding.rvCharacter.adapter
        binding.rvCharacter.apply {
            adapter = concatAdapter
            animation = null
        }
    }
}