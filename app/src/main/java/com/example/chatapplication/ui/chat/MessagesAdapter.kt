package com.example.chatapplication.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.SessionProvider
import com.example.chatapplication.databinding.ItemReceivedMessageBinding
import com.example.chatapplication.databinding.ItemSentMessageBinding
import com.example.chatapplication.model.Message

class MessagesAdapter(var messages: MutableList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class SentMessageViewHolder(val binding: ItemSentMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.setMessage(message)
            binding.executePendingBindings()
        }
    }

    class ReceivedMessageViewHolder(val binding: ItemReceivedMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.setMessage(message)
            binding.executePendingBindings()
        }
    }

    enum class MessageType(val value: Int) {
        Received(200),
        Sent(100)
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        if (message.senderId == SessionProvider.user?.id) {
            return MessageType.Sent.value
        } else {
            return MessageType.Received.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MessageType.Sent.value) {
            val itemBinding =
                ItemSentMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SentMessageViewHolder(itemBinding)
        } else {
            val itemBinding = ItemReceivedMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ReceivedMessageViewHolder(itemBinding)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            //smart casting
            is SentMessageViewHolder -> {
                holder.bind(messages[position])
            }

            is ReceivedMessageViewHolder -> {
                holder.bind(messages[position])
            }
        }

    }

    fun addNewMessages(newMessages: List<Message>?) {
        if (newMessages == null) return
        val startPosition = messages.size
        messages.addAll(newMessages)
        notifyItemRangeChanged(startPosition, newMessages.size)
    }

}