package com.example.todoapp.ui.addedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.dao.ItemDao
import com.example.todoapp.database.model.Item
import com.example.todoapp.utils.models.Date
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.*
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

    var dateString: String =
        state.get<String>("dateString") ?: item?.date ?: getDate()
        set(value) {
            field = value
            state.set("dateString", value)
        }

    private fun getDate() = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

    fun onDateSelectorClick() = viewModelScope.launch {
        val date = getDate()
        val day = date.substring(0, 2).toInt()
        val month = date.substring(3, 5).toInt()
        val year = date.substring(6, 10).toInt()
        eventChannel.send(
            AddEditFragmentEvents.NavigateToDatePickerDialog(Date(day, month, year))
        )
    }

    fun onSaveClick() = viewModelScope.launch {
        try {
            if (isNameCorrect()) {
                itemDao.insertItem(
                    Item(
                        id = null,
                        completed = false,
                        name = nameString,
                        category = categoryInt,
                        date = dateString
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

    fun onCancelClick() = viewModelScope.launch {
        eventChannel.send(
            AddEditFragmentEvents.CancelEditing
        )
    }

    sealed class AddEditFragmentEvents {
        data class NavigateToDatePickerDialog(val date: Date) : AddEditFragmentEvents()
        object ShowSavedMessage : AddEditFragmentEvents()
        object CancelEditing : AddEditFragmentEvents()
        data class ShowNameError(val correctValue: Boolean) : AddEditFragmentEvents()
        data class ShowInsertError(val message: String) : AddEditFragmentEvents()
    }
}