package com.example.todoapp.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor() : ViewModel() {
    private val eventChannel = Channel<MainFragmentEvents>()
    val events = eventChannel.receiveAsFlow()

    fun addItemClick() = viewModelScope.launch {
        eventChannel.send(
            MainFragmentEvents.NavigateToAddEditScreen(null)
        )
    }

    sealed class MainFragmentEvents {
        data class NavigateToAddEditScreen(val item: Item?) : MainFragmentEvents()
    }
}