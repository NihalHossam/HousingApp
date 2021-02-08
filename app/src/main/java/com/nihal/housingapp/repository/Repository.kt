package com.nihal.housingapp.repository

import com.nihal.housingapp.api.ApiHelper
import com.nihal.housingapp.db.HouseDao
import com.nihal.housingapp.models.House
import javax.inject.Inject

/**
 * The repository class is responsible for providing data requested by the ViewModel.
 */
class Repository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val houseDao: HouseDao
): AppRepository{

    /**
     * Gets the houses response from the api.
     */
    override suspend fun getHouses() = apiHelper.getHouses()

    /**
     * Inserts a house object in the database.
     * @param house The house object to be inserted.
     */
    override suspend fun insert(house: House) = houseDao.insertHouse(house)

    /**
     * Deletes a house object in the database.
     * @param house The house object to be deleted.
     */
    override suspend fun delete(house: House) = houseDao.deleteHouse(house)

    /**
     * Gets all the houses in the database by calling the house dao function
     */
    override fun getSavedHouses() = houseDao.getAllHouses()


}