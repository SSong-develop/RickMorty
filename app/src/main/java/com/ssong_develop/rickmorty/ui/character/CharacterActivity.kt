package com.ssong_develop.rickmorty.ui.character

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssong_develop.rickmorty.databinding.ActivityCharacterBinding
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.network.client.Characters
import com.ssong_develop.rickmorty.ui.adapters.CharacterListAdapter
import com.ssong_develop.rickmorty.ui.viewholders.CharacterListViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterActivity : AppCompatActivity(), CharacterListViewHolder.Delegate , CharacterContract.View {

    private lateinit var binding: ActivityCharacterBinding

    private var adapter : CharacterListAdapter? = null

    @Inject
    lateinit var presenter : CharacterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.loadCharacters()
        initializeView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply {
            putInt(LAST_PAGE,presenter.currentPage)
        })
    }

    private fun initializeView() {
        adapter = CharacterListAdapter(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter = null
    }

    override fun showCharacters(list: List<Characters>) {

    }

    override fun onItemClick(view: View, characters: Characters) {
        toast("hello")
    }

    companion object {
        private const val LAST_PAGE = "LAST_PAGE"
    }
}