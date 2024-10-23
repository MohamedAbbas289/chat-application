package com.example.chatapplication.firestore

import com.example.chatapplication.model.Message
import com.example.chatapplication.model.Message.Companion.COLLECTION_NAME
import com.google.android.gms.tasks.OnCompleteListener

object MessagesDao {
    fun getMessagesCollection(roomId: String) =
        RoomsDao.getRoomsCollection()
            .document(roomId)
            .collection(COLLECTION_NAME)

    fun sendMessage(message: Message, onCompleteListener: OnCompleteListener<Void>) {
        val messageDoc = getMessagesCollection(message.roomId ?: "")
            .document()
        message.id = messageDoc.id
        messageDoc.set(message)
            .addOnCompleteListener(onCompleteListener)
    }
}