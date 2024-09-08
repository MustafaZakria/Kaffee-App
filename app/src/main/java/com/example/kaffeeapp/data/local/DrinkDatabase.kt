package com.example.kaffeeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.kaffeeapp.data.entities.Converters
import com.example.kaffeeapp.data.entities.Drink

@Database(entities = [Drink::class], version = 1)
@TypeConverters(Converters::class)
abstract class DrinkDatabase : RoomDatabase() {

    abstract fun getDrinkDao(): DrinkDao
}