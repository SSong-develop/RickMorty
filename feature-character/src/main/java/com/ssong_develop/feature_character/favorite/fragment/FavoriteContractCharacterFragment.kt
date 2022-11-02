package com.ssong_develop.feature_character.favorite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.ssong_develop.core_common.AutoClearBinding
import com.ssong_develop.feature_character.databinding.FragmentFavoriteContractCharacterBinding
import com.ssong_develop.feature_character.favorite.viewmodel.FavoriteContractCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteContractCharacterFragment : Fragment() {

    private var binding: FragmentFavoriteContractCharacterBinding by AutoClearBinding<FragmentFavoriteContractCharacterBinding>()

    private val viewModel: FavoriteContractCharacterViewModel by viewModels()

    companion object {
        fun newInstance(): FavoriteContractCharacterFragment {
            val fragment = FavoriteContractCharacterFragment()
            val bundle = Bundle()
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoriteContractCharacterBinding.inflate(inflater, container, false)
        .also { fragmentFavoriteContractCharacterBinding ->
            binding = fragmentFavoriteContractCharacterBinding
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteCharacter.collectLatest { character ->
                    character?.let {
                        binding.tvFavNameContract.text = it.name
                        Glide.with(binding.ivFavThumbnailContract)
                            .load(it.image)
                            .into(binding.ivFavThumbnailContract)
                    } ?: run {

                    }
                }
            }
        }
    }
}