package com.hazarbelge.themoviedb.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.hazarbelge.themoviedb.ui.main.dependency_injection.homeModule
import com.hazarbelge.themoviedb.ui.launcher.dependency_injection.launcherModule
import com.hazarbelge.themoviedb.network.dependency_injection.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TheMovieDBApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidContext(this@TheMovieDBApp)
            androidLogger(Level.ERROR)
            modules(networkModule + launcherModule + homeModule)
        }
    }
}