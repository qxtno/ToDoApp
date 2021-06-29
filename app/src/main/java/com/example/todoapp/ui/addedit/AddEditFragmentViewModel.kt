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

    var nameString: String =
        state.get<String>("nameString") ?: item?.name ?: ""
        set(value) {
            field = value
            state.set("nameString", value)
        }

    var categoryInt: Int =
        state.get<Int>("categoryInt") ?: item?.category ?: 0
        set(value) {
            field = value
            state.set("categoryInt", value)
        }

    var dateLong: Long =
        state.get<Long>("dateLong") ?: item?.date ?: System.currentTimeMillis()
        set(value) {
            field = value
            state.set("dateLong", value)
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
                        name = nameString,
                        category = categoryInt,
                        date = dateLong
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
        return if (nameString != "") {
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