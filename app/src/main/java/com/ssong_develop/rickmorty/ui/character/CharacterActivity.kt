package com.ssong_develop.rickmorty.ui.character

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ActivityCharacterBinding
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.ui.adapters.CharacterListAdapter
import com.ssong_develop.rickmorty.ui.detail.CharacterDetailActivity
import com.ssong_develop.rickmorty.ui.viewholders.CharacterListViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterActivity : AppCompatActivity(), CharacterListViewHolder.Delegate {

    private val binding: ActivityCharacterBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_character)
    }

    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@CharacterActivity
            vm = viewModel
            adapter = CharacterListAdapter(this@CharacterActivity)
        }
    }

    override fun onItemClick(view: View, characters: Characters) {
        CharacterDetailActivity.startActivity(this, view, characters)
    }
}