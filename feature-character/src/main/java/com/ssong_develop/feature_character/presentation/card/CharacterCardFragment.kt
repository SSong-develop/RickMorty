package com.ssong_develop.feature_character.presentation.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ssong_develop.core_common.AutoClearedValue
import com.ssong_develop.feature_character.databinding.FragmentCharacterCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterCardFragment : Fragment() {

    private var binding: FragmentCharacterCardBinding by AutoClearedValue(this)
    private val viewModel: CharacterCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterCardBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(binding.ivCharacter)
            .load(viewModel.imageUrl)
            .into(binding.ivCharacter)
    }
}