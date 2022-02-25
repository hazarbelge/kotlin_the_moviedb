package com.hazarbelge.themoviedb.ui.launcher.dependency_injection

import com.hazarbelge.themoviedb.ui.launcher.viewmodel.LauncherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val launcherModule = module {
    viewModel { LauncherViewModel(get()) }
}