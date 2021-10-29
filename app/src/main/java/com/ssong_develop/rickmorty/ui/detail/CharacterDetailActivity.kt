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

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
}