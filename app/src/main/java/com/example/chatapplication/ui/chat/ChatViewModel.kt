package com.example.chatapplication.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapplication.SessionProvider
import com.example.chatapplication.common.SingleLiveEvent
import com.example.chatapplication.firestore.MessagesDao
import com.example.chatapplication.model.Message
import com.example.chatapplication.model.Room
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener

class ChatViewModel : ViewModel() {
    var room: Room? = null
    val messageLiveData = MutableLiveData<String>()
    val toastLiveData = SingleLiveEvent<String>()
    val newMessagesLiveData = SingleLiveEvent<List<Message>>()

    fun sendMessage() {
        if (messageLiveData.value.isNullOrBlank()) return
        val message = Message(
            content = messageLiveData.value!!,
            senderName = SessionProvider.user?.userName,
            senderId = SessionProvider.user?.id,
            roomId = room?.id
        )
        MessagesDao
            .sendMessage(message) { task ->
                if (task.isSuccessful) {
                    messageLiveData.value = ""
                    return@sendMessage
                }
                toastLiveData.value = "something went wrong"
            }
    }

    fun changeRoom(room: Room?) {
        this.room = room
        listenForMessagesInRoom()
    }

    fun listenForMessagesInRoom() {
        MessagesDao
            .getMessagesCollection(room?.id ?: "")
            .orderBy("timestamp")
            .limitToLast(50)
            .addSnapshotListener(EventListener { snapshot, error ->
                val newMessages = mutableListOf<Message>()
                snapshot?.documentChanges?.forEach { docChange ->
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val message = docChange.document.toObject(Message::class.java)
                        newMessages.add(message)
                    } else if (docChange.type == DocumentChange.Type.MODIFIED) {

                    } else if (docChange.type == DocumentChange.Type.REMOVED) {

                    }
                }
                newMessagesLiveData.value = newMessages
            })
    }
}