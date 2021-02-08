package com.nihal.housingapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Creates a house object with its details
 *
 * @param id The house's id.
 * @param image The house's image.
 * @param price The house's price in dollars.
 * @param bedrooms The house's number of bedrooms.
 * @param bathrooms The house's number of bathrooms.
 * @param description The house's description.
 * @param zip The house's zipcode.
 * @param city The house's city.
 * @param latitude The house's location's latitude.
 * @param longitude The house's location's longitude.
 **/
@Entity(tableName = "houses")
data class House(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var image: String,
    var price: Int,
    var bedrooms: Int,
    var bathrooms: Int,
    var description: String,
    var zip: String,
    var city: String,
    var latitude: Double,
    var longitude: Double
): Serializable