package com.example.chatapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.databinding.ItemRoomBinding
import com.example.chatapplication.model.Category
import com.example.chatapplication.model.Room

class RoomsRecyclerAdapter(var rooms: List<Room>? = listOf()) :
    RecyclerView.Adapter<RoomsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room?) {
            binding.catImage.setImageResource(
                Category.getCategoryImageByCategoryId(room?.categoryId)
            )
            binding.title.text = room?.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = rooms?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rooms?.get(position))
    }

    fun changeData(rooms: List<Room>?) {
        this.rooms = rooms
        notifyDataSetChanged()
    }
}