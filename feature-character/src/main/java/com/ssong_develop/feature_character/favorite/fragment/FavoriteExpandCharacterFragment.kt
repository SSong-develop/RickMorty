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
import com.ssong_develop.core_common.PixelRatio
import com.ssong_develop.feature_character.databinding.FragmentFavoriteExpandCharacterBinding
import com.ssong_develop.feature_character.favorite.viewmodel.FavoriteExpandCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteExpandCharacterFragment : Fragment() {

    private var binding by AutoClearBinding<FragmentFavoriteExpandCharacterBinding>()

    private val viewModel: FavoriteExpandCharacterViewModel by viewModels()

    @Inject
    lateinit var pixelRatio: PixelRatio

    companion object {
        fun newInstance(): FavoriteExpandCharacterFragment {
            val fragment = FavoriteExpandCharacterFragment()
            val bundle = Bundle()

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoriteExpandCharacterBinding.inflate(inflater, container, false)
        .also { fragmentFavoriteExpandCharacterBinding ->
            binding = fragmentFavoriteExpandCharacterBinding
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteCharacter.collectLatest { character ->
                    character?.let {
                        binding.tvFavExpandCharacterName.text = it.name
                        Glide.with(binding.ivFavExpandCharacter)
                            .load(it.image)
                            .into(binding.ivFavExpandCharacter)
                        binding.ivFavExpandCharacter
                    } ?: run {
                        binding.tvFavExpandCharacterName.text = "PLACE HOLDER"
                    }
                }
            }
        }
    }
}