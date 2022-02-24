package com.hazarbelge.themoviedb.ui.main.views.home.view

import android.os.Bundle

import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL

import com.hazarbelge.themoviedb.ui.main.views.home.viewmodel.HomeViewModel
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.base.BaseActivity
import com.hazarbelge.themoviedb.databinding.ActivityHomeBinding
import com.hazarbelge.themoviedb.databinding.LayoutBottomNavBarBinding
import com.hazarbelge.themoviedb.ui.main.views.home.adapter.HomeViewPagerAdapter
import com.hazarbelge.themoviedb.ui.main.views.now_playing.view.NowPlayingFragment

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {

    override val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    private lateinit var bottomNavBarBinding: LayoutBottomNavBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewPager()
        setupBottomNavigationView()
        setSupportActionBar(binding.topNavBar.toolbar).apply { title = "" }
        setOnclick()
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
                    NowPlayingFragment.newInstance(),
                    NowPlayingFragment.newInstance(),
                    NowPlayingFragment.newInstance(),
                    NowPlayingFragment.newInstance()
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
                R.id.action_now_playing1 -> {
                    binding.viewPager.currentItem = 1
                    true
                }
                R.id.action_now_playing2 -> {
                    binding.viewPager.currentItem = 2
                    true
                }
                R.id.action_now_playing3 -> {
                    binding.viewPager.currentItem = 3
                    true
                }

                R.id.action_now_playing4 -> {
                    binding.viewPager.currentItem = 4
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {}
}