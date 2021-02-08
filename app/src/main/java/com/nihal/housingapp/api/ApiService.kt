package com.nihal.housingapp.api

import com.nihal.housingapp.models.HousesResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * An interface that defines API methods to be used by Retrofit builder.
 */
interface ApiService{

    /**
     *  Defining the request method using the HTTP annotation GET to specify request type
     *  and the relative url.
     *  @return a response with the houses.
     */
    @GET("houses")
    suspend fun getHouses():Response<HousesResponse>
}