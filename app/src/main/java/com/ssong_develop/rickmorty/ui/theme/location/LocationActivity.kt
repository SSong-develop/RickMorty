package com.ssong_develop.rickmorty.ui.theme.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.RickMortyApp.Companion.versionCheckUtils
import com.ssong_develop.rickmorty.databinding.ActivityLocationBinding
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.ui.adapters.LocationListAdapter
import com.ssong_develop.rickmorty.ui.viewholders.LocationListViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationActivity : AppCompatActivity() , LocationListViewHolder.Delegate {

    private val binding: ActivityLocationBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_location)
    }

    private val viewModel: LocationViewModel by viewModels()

    private lateinit var locationAdapter : LocationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        initializePage()
        initializeUI()
    }

    private fun initializeUI() {
        locationAdapter = LocationListAdapter(this)
        binding.rvLocation.apply {
            adapter = locationAdapter
            layoutManager = GridLayoutManager(this@LocationActivity, SPAN_COUNT)
        }
    }

    private fun initializePage() {
        viewModel.initialFetchLocations(intent.getIntExtra("locationPage", 0))
    }

    override fun onItemClick(view: View, location: Location) {
        toast("hello!")
    }

    companion object {

        private const val SPAN_COUNT = 2

        fun startActivityTransition(activity: Activity?, locationPage: Int, view: View) {
            if (activity != null) {
                val intent = Intent(activity, LocationActivity::class.java).apply {
                    putExtra(
                        "locationPage",
                        locationPage
                    )
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

        fun startActivity(activity: Activity?, locationPage: Int) {
            if (activity != null) {
                val intent = Intent(activity, LocationActivity::class.java).apply {
                    putExtra(
                        "locationPage",
                        locationPage
                    )
                }
                activity.startActivity(intent)
            }
        }
    }
}