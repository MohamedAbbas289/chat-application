package com.example.chatapplication.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapplication.databinding.ActivityLoginBinding
import com.example.chatapplication.ui.home.HomeActivity
import com.example.chatapplication.ui.register.RegisterActivity
import com.example.chatapplication.ui.showMessage


class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.messageLiveData.observe(this) { message ->
            showMessage(
                message = message.message ?: "something went wrong",
                posActionName = "ok",
                posAction = message.onPosActionClick,
                negActionName = message.negActionName,
                negAction = message.onNegActionClick,
                isCancelable = message.isCancelable
            )
        }
        viewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(loginViewEvent: LoginViewEvent?) {
        when (loginViewEvent) {
            LoginViewEvent.NavigateToHome -> {
                startHomeActivity()
            }

            LoginViewEvent.NavigateToRegister -> {
                startRegisterActivity()
            }

            else -> {}
        }
    }


    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.content.dontHaveAcc.setOnClickListener {
            startRegisterActivity()
        }
    }

    private fun startRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}