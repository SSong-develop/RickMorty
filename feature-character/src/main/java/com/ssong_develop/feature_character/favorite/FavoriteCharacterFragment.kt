package com.ssong_develop.feature_character.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.databinding.FragmentFavoriteCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteCharacterFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteCharacterBinding

    private val viewModel: FavoriteCharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite_character,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }
}