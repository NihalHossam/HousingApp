package com.nihal.housingapp.di

import com.nihal.housingapp.api.ApiHelper
import com.nihal.housingapp.db.HouseDao
import com.nihal.housingapp.repository.AppRepository
import com.nihal.housingapp.repository.Repository
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
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(apiHelper: ApiHelper, houseDao: HouseDao) = Repository(apiHelper, houseDao) as AppRepository
}