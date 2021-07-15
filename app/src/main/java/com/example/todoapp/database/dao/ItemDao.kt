package com.example.todoapp.database.dao

import androidx.room.*
import com.example.todoapp.database.model.Item
import com.example.todoapp.utils.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    fun getItems(query: String, sortOrder: SortOrder): Flow<List<Item>> =
        when (sortOrder) {
            SortOrder.ASC -> getAllItemsAsc(query)
            SortOrder.DESC -> getAllItemsDesc(query)
        }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Query("SELECT * FROM item WHERE name LIKE '%' || :query || '%' ORDER BY completed ASC, date ASC")
    fun getAllItemsAsc(query: String): Flow<List<Item>>

    @Query("SELECT * FROM item WHERE name LIKE '%' || :query || '%' ORDER BY completed DESC, date DESC")
    fun getAllItemsDesc(query: String): Flow<List<Item>>

    @Query("DELETE FROM Item WHERE completed = 1")
    suspend fun deleteCompletedItems()

    @Delete
    suspend fun deleteItem(item: Item)
}