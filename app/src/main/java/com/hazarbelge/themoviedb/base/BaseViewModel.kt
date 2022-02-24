package com.hazarbelge.themoviedb.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import org.koin.core.component.KoinComponent

open class BaseViewModel : ViewModel(), KoinComponent {

    val serverMessageObserver = MutableLiveData<String?>()

}
