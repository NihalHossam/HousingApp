package com.nihal.housingapp.di

import android.app.Application
import androidx.room.Room
import com.nihal.housingapp.db.HouseDao
import com.nihal.housingapp.db.HousesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * To tell Hilt how to provide instances of this type,
 * to be used for dependency injection.
 */
@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    /**
     * Create a database singleton that will instantiate once using the configured values.
     * @return the created database instance.
     * */
    @Provides
    @Singleton
    fun housesDatabase(application: Application?): HousesDatabase {
        return Room.databaseBuilder(application!!, HousesDatabase::class.java, "houses_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides the house dao to repository.
     * @return The house dao that will be used for constructor injection in repository.
     */
    @Provides
    @Singleton
    fun houseDAO(housesDatabase: HousesDatabase): HouseDao {
        return housesDatabase.getHouseDao()
    }

}