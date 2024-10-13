package com.example.chatapplication.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapplication.SessionProvider
import com.example.chatapplication.common.SingleLiveEvent
import com.example.chatapplication.firestore.UsersDao
import com.example.chatapplication.firestore.model.User
import com.example.chatapplication.ui.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {
    val messageLiveData = SingleLiveEvent<Message>()
    val isLoading = MutableLiveData<Boolean>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    val events = SingleLiveEvent<LoginViewEvent>()

    val auth = Firebase.auth

    private fun validForm(): Boolean {
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
        return true
    }

    fun login() {
        if (!validForm()) return
        isLoading.value = true
        auth.signInWithEmailAndPassword(email.value!!, password.value!!)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    getUserFromFireStore(task.result.user?.uid)
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

    private fun getUserFromFireStore(uid: String?) {
        UsersDao
            .getUser(uid) { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    val user = task.result.toObject(User::class.java)
                    SessionProvider.user = user
                    messageLiveData.postValue(
                        Message(
                            message = "logged in successfully",
                            posActionName = "ok",
                            onPosActionClick = {
                                events.postValue(LoginViewEvent.NavigateToHome)
                            },
                            isCancelable = false
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