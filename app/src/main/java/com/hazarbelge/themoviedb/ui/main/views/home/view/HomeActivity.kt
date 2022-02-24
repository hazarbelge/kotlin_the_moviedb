package com.hazarbelge.themoviedb.ui.main.views.home.view

import android.os.Bundle
import android.view.View

import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL

import com.hazarbelge.themoviedb.ui.main.views.home.viewmodel.HomeViewModel
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.base.BaseActivity
import com.hazarbelge.themoviedb.databinding.ActivityHomeBinding
import com.hazarbelge.themoviedb.databinding.LayoutBottomNavBarBinding
import com.hazarbelge.themoviedb.ui.main.views.home.adapter.HomeViewPagerAdapter
import com.hazarbelge.themoviedb.ui.main.views.latest.view.LatestFragment
import com.hazarbelge.themoviedb.ui.main.views.now_playing.view.NowPlayingFragment
import com.hazarbelge.themoviedb.ui.main.views.popular.view.PopularFragment
import com.hazarbelge.themoviedb.ui.main.views.top_rated.view.TopRatedFragment
import com.hazarbelge.themoviedb.ui.main.views.upcoming.view.UpcomingFragment

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {

    override val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    private lateinit var bottomNavBarBinding: LayoutBottomNavBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewPager()
        setupBottomNavigationView()
        setSupportActionBar(binding.topNavBar.toolbar)
        setOnclick()

        binding.topNavBar.apply {
            title = ""
            returnImageView.visibility = View.GONE
        }
    }

    private fun setOnclick() {

    }

    private fun setupViewPager() {
        with(binding) {
            viewPager.orientation = ORIENTATION_HORIZONTAL
            viewPager.adapter = HomeViewPagerAdapter(
                this@HomeActivity,
                listOf(
                    NowPlayingFragment.newInstance(),
                    PopularFragment.newInstance(),
                    TopRatedFragment.newInstance(),
                    UpcomingFragment.newInstance()
                )
            )
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    //bottomNavBarBinding.bottomNavigationView.menu.getItem(position).isChecked = true
                }
            })
        }
    }

    private fun setupBottomNavigationView() {
        bottomNavBarBinding = LayoutBottomNavBarBinding.bind(binding.root)
        bottomNavBarBinding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_now_playing -> {
                    binding.viewPager.currentItem = 0
                    true
                }
                R.id.action_popular -> {
                    binding.viewPager.currentItem = 1
                    true
                }
                R.id.action_top_rated -> {
                    binding.viewPager.currentItem = 2
                    true
                }

                R.id.action_upcoming -> {
                    binding.viewPager.currentItem = 3
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {}
}