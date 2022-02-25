package com.hazarbelge.themoviedb.views.main.views.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hazarbelge.themoviedb.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    var userName = MutableLiveData<String>()
}