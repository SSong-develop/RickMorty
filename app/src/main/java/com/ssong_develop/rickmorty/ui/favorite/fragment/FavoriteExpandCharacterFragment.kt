package com.ssong_develop.rickmorty.ui.favorite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ssong_develop.core_common.AutoClearBinding
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.FragmentFavoriteExpandCharacterBinding
import com.ssong_develop.rickmorty.ui.favorite.viewmodel.FavoriteExpandCharacterViewModel
import com.ssong_develop.rickmorty.utils.PixelRatio
import dagger.hilt.android.AndroidEntryPoint
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

    }
}