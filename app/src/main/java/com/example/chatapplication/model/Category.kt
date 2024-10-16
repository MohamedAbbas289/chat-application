package com.example.chatapplication.model

import com.example.chatapplication.R

data class Category(
    val id: Int,
    val title: String,
    val imageResourceId: Int
) {
    companion object {
        fun getCategories() = listOf(
            Category(1, "Sports", R.drawable.sports),
            Category(2, "Music", R.drawable.music),
            Category(3, "Movies", R.drawable.movies),
        )

        fun getCategoryImageByCategoryId(categoryId: Int?): Int {
            return when (categoryId) {
                1 -> R.drawable.sports
                2 -> R.drawable.music
                3 -> R.drawable.movies
                else -> R.drawable.sports
            }
        }
    }
}