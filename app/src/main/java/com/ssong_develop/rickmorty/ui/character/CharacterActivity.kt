package com.ssong_develop.rickmorty.ui.character

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityCharacterBinding
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.ui.adapters.CharacterListAdapter
import com.ssong_develop.rickmorty.ui.adapters.CharacterPagingAdapter
import com.ssong_develop.rickmorty.ui.adapters.FooterAdapter
import com.ssong_develop.rickmorty.ui.detail.CharacterDetailActivity
import com.ssong_develop.rickmorty.ui.viewholders.CharacterListViewHolder
import com.ssong_develop.rickmorty.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterActivity : AppCompatActivity(), CharacterListViewHolder.Delegate {

    private val binding: ActivityCharacterBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_character)
    }

    private val viewModel: CharacterViewModel by viewModels()

    private lateinit var pagingAdapter: CharacterPagingAdapter

    private val footerAdapter: FooterAdapter = FooterAdapter()

    private val concatAdapter = ConcatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@CharacterActivity
            vm = viewModel
            adapter = CharacterListAdapter(this@CharacterActivity)
        }
        initAdapter()

        binding.rvCharacter.adapter = concatAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.pagingCharacterFlow.collectLatest {
                        pagingAdapter.submitData(it)
                    }
                }
                launch {
                    pagingAdapter.loadStateFlow.collectLatest {
                        when(it.source.append) {
                            is LoadState.Loading -> {
                                concatAdapter.addAdapter(footerAdapter)
                            }
                            else -> {
                                concatAdapter.removeAdapter(footerAdapter)
                            }
                        }
                    }
                }
                launch {
                    viewModel.favCharacterFlow.collectLatest {
                        when(it.status) {
                            Resource.Status.SUCCESS -> {
                                Log.d("ssong-develop",it.data.toString())
                            }
                            Resource.Status.ERROR -> {
                                Log.d("ssong-develop","error")
                            }
                            Resource.Status.LOADING -> {
                                Log.d("ssong-develop","loading")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onItemClick(view: View, characters: Characters) {
        CharacterDetailActivity.startActivity(this, view, characters)
    }

    private fun initAdapter() {
        pagingAdapter = CharacterPagingAdapter(this)
        concatAdapter.addAdapter(pagingAdapter)
    }
}