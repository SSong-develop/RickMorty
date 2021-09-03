package com.ssong_develop.rickmorty.ui.theme.location

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
import com.ssong_develop.rickmorty.databinding.ActivityLocationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationActivity : AppCompatActivity() {

    private val binding: ActivityLocationBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_location)
    }

    private val viewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        viewModel.initialFetchLocations(intent.getIntExtra("locationPage", 0))

    }

    companion object {

        fun startActivity(activity : Activity?, locationPage : Int , view : View) {
            if(activity != null){
                val intent = Intent(activity,LocationActivity::class.java).apply { putExtra("locationPage",locationPage) }
                if(versionCheckUtils.checkIsMaterialVersion()){
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