package com.example.chatapplication.firestore

import com.example.chatapplication.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

object RoomsDao {
    private fun getRoomsCollection(): CollectionReference {
        val database = Firebase.firestore
        return database.collection(Room.COLLECTION_NAME)
    }

    fun createRoom(
        title: String,
        desc: String,
        ownerId: String,
        categoryId: Int,
        onCompleteListener: OnCompleteListener<Void>
    ) {
        val collection = getRoomsCollection()
        val docRef = collection.document()
        val room = Room(
            id = docRef.id,
            title = title,
            description = desc,
            ownerId = ownerId,
            categoryId = categoryId
        )
        docRef.set(room).addOnCompleteListener(onCompleteListener)
    }

    fun getAllRooms(onCompleteListener: OnCompleteListener<QuerySnapshot>) {
        getRoomsCollection()
            .get()
            .addOnCompleteListener(onCompleteListener)
    }
}