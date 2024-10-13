package com.example.chatapplication.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapplication.SessionProvider
import com.example.chatapplication.common.SingleLiveEvent
import com.example.chatapplication.firestore.UsersDao
import com.example.chatapplication.firestore.model.User
import com.example.chatapplication.ui.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterViewModel : ViewModel() {
    val messageLiveData = SingleLiveEvent<Message>()
    val isLoading = MutableLiveData<Boolean>()
    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()

    val userNameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val passwordConfirmationError = MutableLiveData<String?>()

    val events = SingleLiveEvent<RegisterViewEvent>()

    val auth = Firebase.auth

    private fun validForm(): Boolean {
        if (userName.value.isNullOrBlank()) {
            userNameError.postValue("please enter your username")
            return false
        } else {
            userNameError.postValue(null)
        }
        if (email.value.isNullOrBlank()) {
            emailError.postValue("please enter your email")
            return false
        } else {
            emailError.postValue(null)
        }
        if (password.value.isNullOrBlank()) {
            passwordError.postValue("please enter your password")
            return false
        } else {
            passwordError.postValue(null)
        }
        if (passwordConfirmation.value.isNullOrBlank()) {
            passwordConfirmationError.postValue("please enter your confirmation password")
            return false
        } else {
            passwordConfirmationError.postValue(null)
        }
        if (passwordConfirmation.value != password.value) {
            passwordConfirmationError.postValue("password doesn't match")
            return false
        } else {
            passwordConfirmationError.postValue(null)
        }
        return true
    }

    fun register() {
        if (!validForm()) return
        isLoading.value = true
        auth.createUserWithEmailAndPassword(email.value!!, password.value!!)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    insertUserToFireStore(task.result.user?.uid)

                } else {
                    isLoading.value = false
                    //show error
                    messageLiveData.postValue(
                        Message(
                            message = task.exception?.localizedMessage
                        )
                    )
                }
            }
    }

    private fun insertUserToFireStore(uid: String?) {
        val user = User(
            id = uid,
            userName = userName.value,
            email = email.value
        )
        UsersDao.createUser(user) { task ->
            isLoading.value = false
            if (task.isSuccessful) {
                messageLiveData.postValue(
                    Message(
                        message = "user created successfully",
                        posActionName = "ok",
                        onPosActionClick = {
                            SessionProvider.user = user
                            events.postValue(RegisterViewEvent.NavigateToHome)
                        }
                    )
                )
            } else {
                messageLiveData.postValue(
                    Message(
                        message = task.exception?.localizedMessage
                    )
                )
            }
        }
    }
}