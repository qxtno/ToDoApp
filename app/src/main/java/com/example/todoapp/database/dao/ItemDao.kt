package com.example.todoapp.database.dao

import androidx.room.*
import com.example.todoapp.database.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Query("SELECT * FROM item")
    fun getAllItems(): Flow<List<Item>>

    @Delete
    suspend fun deleteItem(item: Item)
}