package com.example.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.database.dao.ItemDao
import com.example.todoapp.database.model.Item

@Database(
    entities = [Item::class], version = 1
)

abstract class Database :RoomDatabase(){
    abstract fun itemDao(): ItemDao
}