package com.nihal.housingapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nihal.housingapp.models.House

/**
 * An interface that define methods for interacting with the database.
 */
@Dao
interface HouseDao {

    /**
     * Inserts a house object in the database.
     * @param house The house object to be inserted.
     * @return The house's id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHouse(house: House): Long

    /**
     * Deletes a house object in the database.
     * @param house The house object to be deleted.
     */
    @Delete
    suspend fun deleteHouse(house: House)

    /**
     * Returns all the houses in the database.
     * @return A List of houses.
     */
    @Query("select * from houses")
    fun getAllHouses(): LiveData<List<House>>
}
