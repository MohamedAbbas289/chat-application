package com.example.chatapplication.ui.splash

import androidx.lifecycle.ViewModel
import com.example.chatapplication.SessionProvider
import com.example.chatapplication.common.SingleLiveEvent
import com.example.chatapplication.firestore.UsersDao
import com.example.chatapplication.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SplashViewModel : ViewModel() {
    val events = SingleLiveEvent<SplashViewEvent>()
    fun redirect() {
        if (Firebase.auth.currentUser == null) {
            events.postValue(SplashViewEvent.NavigateToLogin)
            return
        }
        UsersDao.getUser(Firebase.auth.currentUser?.uid ?: "", { task ->
            if (!task.isSuccessful) {
                events.postValue(SplashViewEvent.NavigateToLogin)
                return@getUser
            }
            val user = task.result.toObject(User::class.java)
            SessionProvider.user = user
            events.postValue(SplashViewEvent.NavigateToHome)
        })
    }
}