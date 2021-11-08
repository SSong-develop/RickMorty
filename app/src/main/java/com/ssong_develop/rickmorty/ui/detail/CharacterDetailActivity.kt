package com.ssong_develop.rickmorty.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.ssong_develop.rickmorty.databinding.ActivityCharacterDetailBinding
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import javax.inject.Inject

class CharacterDetailActivity : AppCompatActivity() , CharacterDetailContract.CharacterDetailView {

    private var binding: ActivityCharacterDetailBinding? = null

    @Inject
    lateinit var presenter : CharacterDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    companion object {
        private const val CHARACTER = "CHARACTER"

        fun startActivity(activity : Activity?, view : View, character : Characters) {
            if(activity != null){
                val intent = Intent(activity, CharacterDetailActivity::class.java).apply {
                    putExtra(CHARACTER, character)
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ViewCompat.getTransitionName(view)?.let {
                        val options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activity,view,it)
                        activity.startActivity(intent,options.toBundle())
                    }
                } else {
                    activity.startActivity(intent)
                }
            }
        }
    }

    override fun showEpisodes(list: List<Episode>) {
        TODO("Not yet implemented")
    }

    override fun showEpisodeLoading() {
        binding?.pbEpisodeLoading?.visibility = View.VISIBLE
    }

    override fun hideEpisodeLoading() {
        binding?.pbEpisodeLoading?.visibility = View.GONE
    }
}