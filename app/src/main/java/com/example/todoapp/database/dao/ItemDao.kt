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

    @Query("SELECT * FROM item ORDER BY completed ASC, date ASC")
    fun getAllItems(): Flow<List<Item>>

    @Query("DELETE FROM Item WHERE completed = 1")
    suspend fun deleteCompletedItems()
}