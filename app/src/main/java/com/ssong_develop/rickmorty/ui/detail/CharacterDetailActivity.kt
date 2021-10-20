package com.ssong_develop.rickmorty.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.RickMortyApp.Companion.versionCheckUtils
import com.ssong_develop.rickmorty.databinding.ActivityCharacterDetailBinding
import com.ssong_develop.rickmorty.entities.Characters

class CharacterDetailActivity : AppCompatActivity() {

    private val binding: ActivityCharacterDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_character_detail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val characterItem: Characters = intent.getParcelableExtra(CHARACTER)!!
        with(binding) {
            character = characterItem
            activity = this@CharacterDetailActivity
        }
    }

    companion object {
        private const val CHARACTER = "CHARACTER"

        fun startActivity(activity: Activity?, view: View, character: Characters) {
            if (activity != null) {
                val intent = Intent(activity, CharacterDetailActivity::class.java).apply {
                    putExtra(CHARACTER, character)
                }
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