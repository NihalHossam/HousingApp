package com.nihal.housingapp.repository

import androidx.lifecycle.LiveData
import com.nihal.housingapp.models.House
import com.nihal.housingapp.models.HousesResponse
import retrofit2.Response

/**
 * An interface that defines methods from database and api.
 */
interface AppRepository {

    /**
     * Gets the houses response from the api.
     */
    suspend fun getHouses(): Response<HousesResponse>

    /**
     * Inserts a house object in the database.
     * @param house The house object to be inserted.
     */
    suspend fun insert(house: House): Long

    /**
     * Deletes a house object in the database.
     * @param house The house object to be deleted.
     */
    suspend fun delete(house: House)

    /**
     * Gets all the houses in the database by calling the house dao function
     */
    fun getSavedHouses(): LiveData<List<House>>

}