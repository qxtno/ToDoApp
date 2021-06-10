package com.example.todoapp.ui.items.deleteall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.dao.ItemDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllCompletedViewModel @Inject constructor(
    private val itemDao: ItemDao
) : ViewModel() {

    fun onDeleteClick() = viewModelScope.launch {
        itemDao.deleteCompletedItems()
    }
}