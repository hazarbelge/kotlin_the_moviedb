package com.hazarbelge.themoviedb.ui.main.views.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hazarbelge.themoviedb.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    var userName = MutableLiveData<String>()
}