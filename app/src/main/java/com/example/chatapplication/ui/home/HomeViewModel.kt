package com.example.chatapplication.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapplication.SessionProvider
import com.example.chatapplication.common.SingleLiveEvent
import com.example.chatapplication.firestore.RoomsDao
import com.example.chatapplication.model.Room
import com.example.chatapplication.ui.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class HomeViewModel : ViewModel() {
    val events = SingleLiveEvent<HomeViewEvent>()
    val roomsLiveData = MutableLiveData<List<Room>>()
    val isLoading = MutableLiveData<Boolean>()
    val messageLiveData = SingleLiveEvent<Message>()
    fun navigateToAddRoom() {
        events.postValue(HomeViewEvent.NavigateToAddRoom)
    }

    fun loadRooms() {
        RoomsDao
            .getAllRooms { task ->
                if (!task.isSuccessful) {
                    //show message
                    return@getAllRooms
                }
                val rooms = task.result.toObjects(Room::class.java)
                roomsLiveData.postValue(rooms)
            }
    }

    fun logout() {
        messageLiveData.postValue(
            Message(
                message = "are you sure you want to logout?",
                posActionName = "yes",
                negActionName = "cancel",
                onPosActionClick = {
                    isLoading.postValue(true)
                    Firebase.auth.signOut()
                    SessionProvider.user = null
                    isLoading.postValue(false)
                    events.postValue(HomeViewEvent.NavigateToLogin)
                }
            ))

    }

}