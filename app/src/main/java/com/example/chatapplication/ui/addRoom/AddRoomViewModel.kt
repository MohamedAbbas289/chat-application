package com.example.chatapplication.ui.addRoom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapplication.SessionProvider
import com.example.chatapplication.common.SingleLiveEvent
import com.example.chatapplication.firestore.RoomsDao
import com.example.chatapplication.model.Category
import com.example.chatapplication.ui.Message

class AddRoomViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()

    val events = SingleLiveEvent<AddRoomViewEvent>()
    val roomName = MutableLiveData<String>()
    val roomNameError = MutableLiveData<String?>()
    val roomDescription = MutableLiveData<String>()
    val roomDescriptionError = MutableLiveData<String?>()
    val categories = Category.getCategories()
    var selectedCategory = categories[0]
    val messageLiveData = SingleLiveEvent<Message>()

    fun createRoom() {
        if (!validForm()) return
        isLoading.value = true
        RoomsDao.createRoom(
            title = roomName.value ?: "",
            desc = roomDescription.value ?: "",
            ownerId = SessionProvider.user?.id ?: "",
            categoryId = selectedCategory.id,
        ) { task ->
            isLoading.value = false
            if (task.isSuccessful) {
                messageLiveData.postValue(
                    Message(
                        message = "room created successfully",
                        posActionName = "ok",
                        onPosActionClick = {
                            events.postValue(AddRoomViewEvent.NavigateToHomeAndFinish)
                        }
                    )
                )
                return@createRoom
            }
            messageLiveData.postValue(
                Message(
                    message = "something went wrong: ${task.exception?.localizedMessage}",
                    posActionName = "ok"
                )
            )

        }
    }

    private fun validForm(): Boolean {
        var isValid = true
        if (roomName.value.isNullOrBlank()) {
            roomNameError.postValue("please enter room title")
            isValid = false
        } else {
            roomNameError.postValue(null)
        }
        if (roomDescription.value.isNullOrBlank()) {
            roomDescriptionError.postValue("please enter room description")
            isValid = false
        } else {
            roomDescriptionError.postValue(null)
        }
        return isValid
    }

    fun onCategorySelected(position: Int) {
        selectedCategory = categories[position]
    }
}