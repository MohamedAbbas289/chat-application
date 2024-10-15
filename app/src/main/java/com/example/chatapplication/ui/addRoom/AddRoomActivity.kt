package com.example.chatapplication.ui.addRoom

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapplication.R
import com.example.chatapplication.databinding.ActivityAddRoomBinding
import com.example.chatapplication.ui.showMessage

class AddRoomActivity : AppCompatActivity() {
    private val viewModel: AddRoomViewModel by viewModels()
    private lateinit var binding: ActivityAddRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_room)
        initViews()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.messageLiveData.observe(this) {
            showMessage(
                it.message ?: "",
                posActionName = it.posActionName,
                posAction = it.onPosActionClick,
                negActionName = it.negActionName,
                negAction = it.onNegActionClick,
                isCancelable = it.isCancelable
            )
        }
        viewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(addRoomViewEvent: AddRoomViewEvent?) {
        when (addRoomViewEvent) {
            AddRoomViewEvent.NavigateToHomeAndFinish -> {
                finish()
            }

            else -> {}
        }
    }


    lateinit var categoriesAdapter: RoomCategoryAdapter
    private fun initViews() {
        categoriesAdapter = RoomCategoryAdapter(viewModel.categories)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.content.categories.adapter = categoriesAdapter
        binding.content.categories.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemView: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.onCategorySelected(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }
}