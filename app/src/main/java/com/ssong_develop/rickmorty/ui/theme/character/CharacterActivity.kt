package com.ssong_develop.rickmorty.ui.theme.character

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.RickMortyApp.Companion.versionCheckUtils
import com.ssong_develop.rickmorty.databinding.ActivityCharacterBinding
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.ui.adapters.CharacterListAdapter
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
        }
        initializeUI()
    }

    private fun initializeUI() {
        with(binding.rvCharacter){
            layoutManager = GridLayoutManager(this@CharacterActivity, SPAN_COUNT)
            adapter = CharacterListAdapter(this@CharacterActivity)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = binding.rvCharacter.layoutManager

                    val lastVisibleItem =
                        (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()

                    if (layoutManager.itemCount <= lastVisibleItem + SPAN_COUNT) {
                        viewModel.morePage()
                    }
                }
            })
        }
    }

    override fun onItemClick(view: View, characters: Characters) {
        toast("hello!")
    }

    companion object {
        private const val SPAN_COUNT = 2

        fun startActivityTransition(activity: Activity?, view: View) {
            if (activity != null) {
                val intent = Intent(
                    activity,
                    CharacterActivity::class.java
                )
                if (versionCheckUtils.checkIsMaterialVersion()) {
                    ViewCompat.getTransitionName(view)?.let {
                        val options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, it)
                        activity.startActivity(intent, options.toBundle())
                    }
                } else {
                    activity.startActivity(intent)
                }
            }
        }
    }
}