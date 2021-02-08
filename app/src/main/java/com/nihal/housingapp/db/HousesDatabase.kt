package com.nihal.housingapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nihal.housingapp.models.House

/**
 * The database class for Room that serves as the main access point to it.
 */
@Database(entities = [House::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HousesDatabase : RoomDatabase() {

    /**
     * Gets a house dao.
     * @return A house data access object.
     */
    abstract fun getHouseDao(): HouseDao
}