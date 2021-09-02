package com.ssong_develop.rickmorty.ui.details.episode

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
import com.ssong_develop.rickmorty.RickMortyApp
import com.ssong_develop.rickmorty.databinding.ActivityEpisodeBinding
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.ui.details.location.LocationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeActivity : AppCompatActivity() {

    private val binding: ActivityEpisodeBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_episode)
    }

    private val viewModel: EpisodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        viewModel.initialFetchEpisodes(intent.getIntExtra("episodePage", 0))
        viewModel.episodes.observe(this){
            toast(it.toString())
        }
    }

    companion object {

        fun startActivity(activity : Activity?, episodePage : Int, view : View) {
            if(activity != null){
                val intent = Intent(activity, EpisodeActivity::class.java).apply { putExtra("episodePage",episodePage) }
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