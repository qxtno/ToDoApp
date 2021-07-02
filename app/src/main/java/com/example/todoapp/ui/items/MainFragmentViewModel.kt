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
            MainFragmentEvents.NavigateToAddEditScreen(null)
        )
    }

    fun onItemClick(item: Item) = viewModelScope.launch {
        eventChannel.send(
            MainFragmentEvents.NavigateToAddEditScreen(item)
        )
    }

    fun onItemChecked(item: Item, isChecked: Boolean) = viewModelScope.launch {
        itemDao.updateItem(item.copy(completed = isChecked))
    }

    fun onDeleteAllClick() = viewModelScope.launch {
        var areItemsSelected = false

        items.value?.forEach {
            if (it.completed) {
                areItemsSelected = true
            }
        }

        if (items.value?.isNotEmpty() == true && areItemsSelected) {
            eventChannel.send(
                MainFragmentEvents.NavigateToDeleteAllDialog
            )
        } else {
            eventChannel.send(
                MainFragmentEvents.ShowCannotDeleteMessage
            )
        }
    }

    fun onItemSwiped(item: Item) = viewModelScope.launch {
        itemDao.deleteItem(item)
        eventChannel.send(
            MainFragmentEvents.ShowUndoDeleteMessage(item)
        )
    }

    fun onUndoDelete(item: Item) = viewModelScope.launch {
        itemDao.insertItem(item)
    }

    sealed class MainFragmentEvents {
        data class NavigateToAddEditScreen(val item: Item?) :
            MainFragmentEvents()

        object NavigateToDeleteAllDialog : MainFragmentEvents()
        object ShowCannotDeleteMessage : MainFragmentEvents()
        data class ShowUndoDeleteMessage(val item: Item) : MainFragmentEvents()
    }
}