package com.example.chatapplication.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapplication.R
import com.example.chatapplication.databinding.ActivityHomeBinding
import com.example.chatapplication.ui.addRoom.AddRoomActivity
import com.example.chatapplication.ui.login.LoginActivity
import com.example.chatapplication.ui.showMessage

class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initViews()
        subscribeToLiveData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadRooms()
    }

    private fun subscribeToLiveData() {
        viewModel.events.observe(this, ::handleEvents)
        viewModel.roomsLiveData.observe(this) { rooms ->
            adapter.changeData(rooms)
        }
        viewModel.messageLiveData.observe(this) {
            showMessage(
                it.message ?: "",
                posActionName = it.posActionName,
                posAction = it.onPosActionClick,
                negActionName = it.negActionName,
                negAction = it.onNegActionClick,
                isCancelable = it.isCancelable
            )
        }
    }

    private fun handleEvents(homeViewEvent: HomeViewEvent?) {
        when (homeViewEvent) {
            HomeViewEvent.NavigateToAddRoom -> {
                navigateToAddRoom()
            }

            HomeViewEvent.NavigateToLogin -> {
                navigateToLogin()
            }

            else -> {}
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToAddRoom() {
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
    }

    val adapter = RoomsRecyclerAdapter()
    private fun initViews() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.content.roomsRecycler.adapter = adapter
    }
}