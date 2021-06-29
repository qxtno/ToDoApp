package com.example.todoapp.ui.addedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.dao.ItemDao
import com.example.todoapp.database.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.sql.SQLException
import javax.inject.Inject

@HiltViewModel
class AddEditFragmentViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val itemDao: ItemDao
) : ViewModel() {
    private val eventChannel = Channel<AddEditFragmentEvents>()
    val events = eventChannel.receiveAsFlow()

    val item = state.get<Item>("item")

    var name: String =
        state.get<String>("name") ?: item?.name ?: ""
        set(value) {
            field = value
            state.set("name", value)
        }

    var category: Int =
        state.get<Int>("category") ?: item?.category ?: 0
        set(value) {
            field = value
            state.set("category", value)
        }

    var date: Long =
        state.get<Long>("date") ?: item?.date ?: System.currentTimeMillis()
        set(value) {
            field = value
            state.set("date", value)
        }

    fun onDateSelectorClick() = viewModelScope.launch {
        eventChannel.send(
            AddEditFragmentEvents.NavigateToDatePickerDialog
        )
    }

    fun onSaveClick() = viewModelScope.launch {
        try {
            if (isNameCorrect()) {
                itemDao.insertItem(
                    Item(
                        id = item?.id,
                        completed = item?.completed ?: false,
                        name = name,
                        category = category,
                        date = date
                    )
                )

                eventChannel.send(
                    AddEditFragmentEvents.ShowSavedMessage
                )
            }
        } catch (e: SQLException) {
            eventChannel.send(
                AddEditFragmentEvents.ShowInsertError(e.message.toString())
            )
        }
    }

    fun isNameCorrect(): Boolean {
        return if (name != "") {
            setNameError(false)
            true
        } else {
            setNameError(true)
            false
        }
    }

    private fun setNameError(correctValue: Boolean) = viewModelScope.launch {
        eventChannel.send(
            AddEditFragmentEvents.ShowNameError(correctValue)
        )
    }

    sealed class AddEditFragmentEvents {
        object NavigateToDatePickerDialog : AddEditFragmentEvents()
        object ShowSavedMessage : AddEditFragmentEvents()
        data class ShowNameError(val correctValue: Boolean) : AddEditFragmentEvents()
        data class ShowInsertError(val message: String) : AddEditFragmentEvents()
    }
}