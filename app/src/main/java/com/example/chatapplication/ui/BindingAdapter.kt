package com.example.chatapplication.ui

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:error")
fun bindErrorOnTextFieldLayout(
    textInputLayout: TextInputLayout,
    errorMessage: String?
) {
    textInputLayout.error = errorMessage
}