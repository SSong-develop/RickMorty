package com.ssong_develop.rickmorty.ui.character

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.ssong_develop.core_model.Characters
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityCharacterBinding
import com.ssong_develop.rickmorty.ui.adapters.CharacterPagingAdapter
import com.ssong_develop.rickmorty.ui.adapters.FooterAdapter
import com.ssong_develop.rickmorty.ui.detail.CharacterDetailActivity
import com.ssong_develop.rickmorty.ui.viewholders.CharacterListViewHolder
import com.ssong_develop.rickmorty.utils.PixelRatio
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterActivity : AppCompatActivity(), CharacterListViewHolder.Delegate {

    private val binding: ActivityCharacterBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_character)
    }

    private val viewModel: CharacterViewModel by viewModels()

    private lateinit var behavior: BottomSheetBehavior<*>

    private lateinit var pagingAdapter: CharacterPagingAdapter

    private val footerAdapter: FooterAdapter = FooterAdapter()

    private val concatAdapter = ConcatAdapter()

    @Inject
    lateinit var pixelRatio: PixelRatio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@CharacterActivity
            vm = viewModel
        }
        initAdapter()
        initRecyclerView()

        behavior = BottomSheetBehavior.from(binding.favCharacterSheet)

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    STATE_EXPANDED -> {
                        val navHostFragment =
                            supportFragmentManager.findFragmentById(R.id.fav_character_sheet) as NavHostFragment
                        navHostFragment.navController.navigate(
                            resId = R.id.favoriteExpandCharacterFragment,
                            args = null,
                            navOptions = null,
                        )
                    }
                    else -> {
                        val navHostFragment =
                            supportFragmentManager.findFragmentById(R.id.fav_character_sheet) as NavHostFragment
                        navHostFragment.navController.navigate(
                            resId = R.id.favoriteContractCharacterFragment,
                            args = null,
                            navOptions = null
                        )
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        lifecycleScope.launch {
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
    }

    override fun onItemClick(view: View, characters: Characters) {
        CharacterDetailActivity.startActivity(this, view, characters)
    }

    private fun initAdapter() {
        pagingAdapter = CharacterPagingAdapter(this)
        concatAdapter.addAdapter(pagingAdapter)
    }

    private fun initRecyclerView() {
        binding.rvCharacter.adapter = concatAdapter
    }
}