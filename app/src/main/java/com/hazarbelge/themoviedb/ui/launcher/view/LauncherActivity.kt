package com.hazarbelge.themoviedb.ui.launcher.view

import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.base.BaseActivity
import com.hazarbelge.themoviedb.common.HOME_ACTIVITY_PACKAGE_PATH
import com.hazarbelge.themoviedb.databinding.ActivityLauncherBinding
import com.hazarbelge.themoviedb.ui.launcher.viewmodel.LauncherViewModel

class LauncherActivity : BaseActivity<LauncherViewModel, ActivityLauncherBinding>() {

    override val binding: ActivityLauncherBinding by lazy {
        ActivityLauncherBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        setOnClicks()
    }

    private fun setOnClicks() {
        val movieButton = findViewById<MaterialButton>(R.id.movieButton)

        movieButton.setOnClickListener {
            startActivity(HOME_ACTIVITY_PACKAGE_PATH)
        }
    }
}