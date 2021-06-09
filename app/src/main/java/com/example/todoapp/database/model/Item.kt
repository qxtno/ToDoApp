package com.example.todoapp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey
    val id: Long?,
    val completed: Boolean,
    val name: String,
    val category: String,
    val date: String
)