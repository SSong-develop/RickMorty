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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.RickMortyApp.Companion.versionCheckUtils
import com.ssong_develop.rickmorty.databinding.ActivityCharacterBinding
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.ui.adapters.CharacterListAdapter
import com.ssong_develop.rickmorty.ui.viewholders.CharacterListViewHolder
import com.ssong_develop.rickmorty.utils.observeOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterActivity : AppCompatActivity(), CharacterListViewHolder.Delegate {

    private val binding: ActivityCharacterBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_character)
    }

    private val viewModel: CharacterViewModel by viewModels()

    private lateinit var characterAdapter: CharacterListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        initializeUI()
        initializeCollect()
    }

    private fun initializeUI() {
        characterAdapter = CharacterListAdapter(this)
        binding.rvCharacter.apply {
            layoutManager = GridLayoutManager(this@CharacterActivity, SPAN_COUNT)
            adapter = characterAdapter
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

    private fun initializeCollect() {
        viewModel.characters.observeOnLifecycle(this){
            characterAdapter.submitList(it)
        }

        viewModel.loading.observeOnLifecycle(this){
            binding.pbCharacter.run {
                if (it) this.visibility = View.VISIBLE
                else this.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(view: View, characters: Characters) {
        toast("hello!")
    }

    companion object {
        const val SPAN_COUNT = 2

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

        fun startActivity(activity: Activity?, characterPage: Int) {
            if (activity != null) {
                val intent = Intent(
                    activity,
                    CharacterActivity::class.java
                ).apply { putExtra("characterPage", characterPage) }
                activity.startActivity(intent)
            }
        }
    }
}