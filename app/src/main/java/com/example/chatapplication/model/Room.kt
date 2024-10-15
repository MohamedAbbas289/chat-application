package com.example.chatapplication.model

data class Room(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val categoryId: Int? = null,
    val ownerId: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "rooms"
    }
}
