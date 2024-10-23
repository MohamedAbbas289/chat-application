package com.example.chatapplication.ui.chat

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.R
import com.example.chatapplication.databinding.ActivityChatBinding
import com.example.chatapplication.model.Room
import com.example.chatapplication.ui.Constants.EXTRA_ROOM

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    val viewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initParams()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.toastLiveData.observe(this) { errorMessage ->
            showToast(errorMessage)
        }
        viewModel.newMessagesLiveData.observe(this) { newMessages ->
            messagesAdapter.addNewMessages(newMessages)
            binding.content.messagesRecycler.smoothScrollToPosition(
                messagesAdapter.itemCount
            )
        }
    }

    private fun showToast(errorMessage: String?) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }


    private fun initParams() {
        val room =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_ROOM, Room::class.java)
            } else {
                intent.getParcelableExtra(EXTRA_ROOM) as Room?
            }
        viewModel.changeRoom(room)
    }

    val messagesAdapter = MessagesAdapter(mutableListOf())
    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.content.messagesRecycler.adapter = messagesAdapter
        (binding.content.messagesRecycler.layoutManager as LinearLayoutManager)
            .stackFromEnd = true
    }
}