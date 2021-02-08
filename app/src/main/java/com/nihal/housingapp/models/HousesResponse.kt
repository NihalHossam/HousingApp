package com.nihal.housingapp.models

/**
 * Creates a house response data class with it's properties
 *
 * @param houses A List of houses.
 * @param status The status got from the api.
 */
data class HousesResponse(
    val houses: List<House>?=null,
    val status: String?=""
)