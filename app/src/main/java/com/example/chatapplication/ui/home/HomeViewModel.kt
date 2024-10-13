package com.example.chatapplication.ui.home

import androidx.lifecycle.ViewModel
import com.example.chatapplication.common.SingleLiveEvent

class HomeViewModel : ViewModel() {
    val events = SingleLiveEvent<HomeViewEvent>()
}