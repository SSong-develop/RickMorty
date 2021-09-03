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
    }

    private fun initializeUI() {
        viewModel.initialFetchCharacters(intent.getIntExtra("characterPage", 0))
        characterAdapter = CharacterListAdapter(this)
        binding.rvCharacter.apply {
            layoutManager = GridLayoutManager(this@CharacterActivity, GRID_SPAN_COUNT)
            adapter = characterAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = binding.rvCharacter.layoutManager

                    val lastVisibleItem =
                        (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()

                    // GridLayout SpanCount가 2이기 때문에 2개 모자란 경우에 loadMore하도록 함
                    if (layoutManager.itemCount <= lastVisibleItem + GRID_SPAN_COUNT) {
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

        const val GRID_SPAN_COUNT = 2

        fun startActivityTransition(activity: Activity?, characterPage: Int, view: View) {
            if (activity != null) {
                val intent = Intent(
                    activity,
                    CharacterActivity::class.java
                ).apply { putExtra("characterPage", characterPage) }
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