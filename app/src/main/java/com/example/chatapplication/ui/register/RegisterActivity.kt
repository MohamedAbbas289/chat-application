package com.example.chatapplication.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapplication.databinding.ActivityRegisterBinding
import com.example.chatapplication.ui.home.HomeActivity
import com.example.chatapplication.ui.login.LoginActivity
import com.example.chatapplication.ui.showMessage

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

    private fun handleEvents(registerViewEvent: RegisterViewEvent?) {
        when (registerViewEvent) {
            RegisterViewEvent.NavigateToHome -> {
                startHomeActivity()
            }

            RegisterViewEvent.NavigateToLogin -> {
                startLoginActivity()
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
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.content.alreadyHaveAcc.setOnClickListener {
            startLoginActivity()
        }
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}