package com.ssong_develop.rickmorty.ui.details.character

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.RickMortyApp
import com.ssong_develop.rickmorty.databinding.ActivityCharacterBinding
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.ui.details.location.LocationActivity
import com.ssong_develop.rickmorty.utils.AutoClearedValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterActivity : AppCompatActivity() {

    private val binding : ActivityCharacterBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_character)
    }

    private val viewModel : CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        viewModel.initialFetchCharacters(intent.getIntExtra("characterPage",0))
        viewModel.characters.observe(this){
            toast(it.toString())
        }
    }

    companion object {

        fun startActivity(activity : Activity?, characterPage : Int, view : View) {
            if(activity != null){
                val intent = Intent(activity, CharacterActivity::class.java).apply { putExtra("characterPage",characterPage) }
                if(RickMortyApp.versionCheckUtils.checkIsMaterialVersion()){
                    ViewCompat.getTransitionName(view)?.let {
                        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,view,it)
                        activity.startActivity(intent,options.toBundle())
                    }
                }else{
                    activity.startActivity(intent)
                }
            }
        }
    }
}