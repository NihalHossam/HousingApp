package com.nihal.housingapp.db

import androidx.room.TypeConverter
import com.nihal.housingapp.models.Source

/**
 * A converter class that Room can use to convert our house to source and vice versa
 * since house is not a primitive data type.
 */

class Converters {

    @TypeConverter
    fun fromHouse(source: Source): Int{
        return source.id
    }

    @TypeConverter
    fun toSource(id: Int): Source {
        return Source(id)
    }
}