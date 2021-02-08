package com.nihal.housingapp.api

import com.nihal.housingapp.models.HousesResponse
import retrofit2.Response

/**
 * An interface that defines a method that is used to fetch data from the api.
 */
interface ApiHelper {

    /**
     * @return The houses response from the api
     */
    suspend fun getHouses():Response<HousesResponse>
}