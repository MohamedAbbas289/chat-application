package com.example.chatapplication.ui.addRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.chatapplication.databinding.ItemRoomCategoryBinding
import com.example.chatapplication.model.Category

class RoomCategoryAdapter(val items: List<Category>) : BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        if (view == null) {
            val binding = ItemRoomCategoryBinding
                .inflate(LayoutInflater.from(parent?.context), parent, false)
            viewHolder = ViewHolder(binding)
            binding.root.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.bind(items[position])
        return viewHolder.binding.root
    }

    class ViewHolder(val binding: ItemRoomCategoryBinding) {
        fun bind(item: Category) {
            //bind
            binding.image.setImageResource(item.imageResourceId)
            binding.title.text = item.title
        }
    }
}