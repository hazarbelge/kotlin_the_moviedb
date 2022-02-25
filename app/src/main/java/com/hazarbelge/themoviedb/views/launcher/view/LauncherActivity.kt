package com.hazarbelge.themoviedb.views.launcher.view

import android.os.Bundle
import com.hazarbelge.themoviedb.base.BaseActivity
import com.hazarbelge.themoviedb.common.HOME_ACTIVITY_PACKAGE_PATH
import com.hazarbelge.themoviedb.databinding.ActivityLauncherBinding
import com.hazarbelge.themoviedb.views.launcher.viewmodel.LauncherViewModel

class LauncherActivity : BaseActivity<LauncherViewModel, ActivityLauncherBinding>() {

    override val binding: ActivityLauncherBinding by lazy {
        ActivityLauncherBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClicks()
    }

    private fun setOnClicks() {
        binding.movieButton.setOnClickListener {
            startActivity(HOME_ACTIVITY_PACKAGE_PATH)
        }
    }
}