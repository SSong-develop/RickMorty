package com.ssong_develop.rickmorty.ui.favorite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ssong_develop.core_common.AutoClearBinding
import com.ssong_develop.rickmorty.databinding.FragmentFavoriteContractCharacterBinding
import com.ssong_develop.rickmorty.ui.favorite.viewmodel.FavoriteContractCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteContractCharacterFragment : Fragment() {

    private var binding by AutoClearBinding<FragmentFavoriteContractCharacterBinding>()

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

    }
}