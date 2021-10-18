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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.RickMortyApp.Companion.versionCheckUtils
import com.ssong_develop.rickmorty.databinding.ActivityLocationBinding
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.extensions.toast
import com.ssong_develop.rickmorty.ui.adapters.LocationListAdapter
import com.ssong_develop.rickmorty.ui.viewholders.LocationListViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationActivity : AppCompatActivity(), LocationListViewHolder.Delegate {

    private val binding: ActivityLocationBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_location)
    }

    private val viewModel: LocationViewModel by viewModels()

    private lateinit var locationAdapter: LocationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@LocationActivity
            vm = viewModel
        }
        initializeUI()
    }

    private fun initializeUI() {
        locationAdapter = LocationListAdapter(this)
        binding.rvLocation.apply {
            adapter = locationAdapter
            layoutManager = GridLayoutManager(this@LocationActivity, SPAN_COUNT)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = binding.rvLocation.layoutManager

                    val lastVisibleItem =
                        (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()

                    if (layoutManager.itemCount <= lastVisibleItem + SPAN_COUNT) {
                        viewModel.morePage()
                    }
                }
            })
        }
    }

    override fun onItemClick(view: View, location: Location) {
        toast("hello!")
    }

    companion object {
        private const val SPAN_COUNT = 2

        fun startActivityTransition(activity: Activity?, view: View) {
            if (activity != null) {
                val intent = Intent(activity, LocationActivity::class.java)
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