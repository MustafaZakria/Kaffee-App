package com.example.kaffeeapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kaffeeapp.data.entities.Drink
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(drinks: List<Drink>)

    @Query("SELECT * FROM drinks WHERE id like :id")
    suspend fun getDrinkById(id: String): Drink

    @Query("SELECT * FROM drinks")
    fun getAllDrinks(): LiveData<List<Drink>>

    @Query("SELECT * FROM drinks WHERE type = 'HOT'")
    fun getAllHotDrinks(): LiveData<List<Drink>>

    @Query("SELECT * FROM drinks WHERE type = 'COLD'")
    fun getAllColdDrinks(): LiveData<List<Drink>>
}