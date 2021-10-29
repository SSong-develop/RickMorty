package com.ssong_develop.rickmorty.ui.character

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssong_develop.rickmorty.databinding.ActivityCharacterBinding
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.ui.adapters.CharacterListAdapter
import com.ssong_develop.rickmorty.ui.detail.CharacterDetailActivity
import com.ssong_develop.rickmorty.ui.viewholders.CharacterListViewHolder
import com.ssong_develop.rickmorty.utils.RecyclerViewPaginator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterActivity : AppCompatActivity(), CharacterListViewHolder.Delegate,
    CharacterContract.View {

    private var binding: ActivityCharacterBinding? = null

    private var characterListAdapter: CharacterListAdapter? = null

    @Inject
    lateinit var presenter: CharacterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        savedInstanceState?.let {
            presenter.setPage(it.getInt(LAST_PAGE))
        }
        presenter.loadCharacters()
        initializeView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply {
            putInt(LAST_PAGE, presenter.currentPage)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        characterListAdapter = null
        binding = null
    }

    private fun initializeView() {
        characterListAdapter = CharacterListAdapter(this)
        binding?.rvCharacter?.let {
            it.adapter = characterListAdapter
            RecyclerViewPaginator(
                it,
                loadMore = { presenter.morePage() }
            )
        }
        binding?.swipeRefresh?.let {
            it.setOnRefreshListener {
                presenter.resetPage()
                it.isRefreshing = false
            }
        }
    }

    override fun showCharacters(list: List<Characters>) {
        showLoading()
        characterListAdapter?.submitList(list)
        hideLoading()
    }

    override fun showLoading() {
        binding?.pbCharacter?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding?.pbCharacter?.visibility = View.GONE
    }

    override fun onItemClick(view: View, characters: Characters) {
        CharacterDetailActivity.startActivity(this, view, characters)
    }

    companion object {
        private const val LAST_PAGE = "LAST_PAGE"
    }
}