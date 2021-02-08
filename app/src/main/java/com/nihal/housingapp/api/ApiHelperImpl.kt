package com.nihal.housingapp.api

import com.nihal.housingapp.models.HousesResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * A class that implements the APIHelper interface methods.
 */
class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper{

    /**
     * @return The houses response from the api
     */
    override suspend fun getHouses(): Response<HousesResponse>  = apiService.getHouses()

}