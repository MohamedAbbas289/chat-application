package com.example.chatapplication.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplication.R
import com.example.chatapplication.ui.home.HomeActivity
import com.example.chatapplication.ui.login.LoginActivity

class SplashScreen : AppCompatActivity() {
    val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        subscribeToLiveData()
        Handler(Looper.getMainLooper())
            .postDelayed({
                viewModel.redirect()
            }, 2000)
    }

    private fun subscribeToLiveData() {
        viewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(splashViewEvent: SplashViewEvent?) {
        when (splashViewEvent) {
            SplashViewEvent.NavigateToHome -> startHomeActivity()
            SplashViewEvent.NavigateToLogin -> startLoginActivity()
            else -> {}
        }
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}