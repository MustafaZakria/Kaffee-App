package com.example.kaffeeapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo.Companion.NOCASE
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

    @Query("SELECT * FROM drinks WHERE type like 'HOT' COLLATE NOCASE")
    fun getAllHotDrinks(): LiveData<List<Drink>>

    @Query("SELECT * FROM drinks WHERE type like 'COLD' COLLATE NOCASE")
    fun getAllColdDrinks(): LiveData<List<Drink>>

    @Query("SELECT * FROM drinks WHERE ingredients like :ingredient COLLATE NOCASE")
    fun getDrinksByIngredients(ingredient: String): LiveData<List<Drink>>

    @Query("SELECT * FROM drinks WHERE name like :drink COLLATE NOCASE")
    fun getDrinksBySearch(drink: String): LiveData<List<Drink>>
}