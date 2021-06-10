package com.example.todoapp.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Item(
    @PrimaryKey
    val id: Long?,
    val completed: Boolean,
    val name: String,
    val category: Int,
    val date: String
) : Parcelable