package com.example.todoapp.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.dao.ItemDao
import com.example.todoapp.database.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val itemDao: ItemDao
) : ViewModel() {
    private val eventChannel = Channel<MainFragmentEvents>()
    val events = eventChannel.receiveAsFlow()

    val items = itemDao.getAllItems().asLiveData()

    fun addItemClick() = viewModelScope.launch {
        eventChannel.send(
            MainFragmentEvents.NavigateToAddEditScreen(null, true)
        )
    }

    fun onItemClick(item: Item) = viewModelScope.launch {
        eventChannel.send(
            MainFragmentEvents.NavigateToAddEditScreen(item, false)
        )
    }

    fun onItemChecked(item: Item, isChecked: Boolean) = viewModelScope.launch {
        itemDao.updateItem(item.copy(completed = isChecked))
    }

    fun onDeleteAllClick() = viewModelScope.launch {
        eventChannel.send(
            MainFragmentEvents.NavigateToDeleteAllDialog
        )
    }

    sealed class MainFragmentEvents {
        data class NavigateToAddEditScreen(val item: Item?, val newItem: Boolean) :
            MainFragmentEvents()

        object NavigateToDeleteAllDialog : MainFragmentEvents()
    }
}