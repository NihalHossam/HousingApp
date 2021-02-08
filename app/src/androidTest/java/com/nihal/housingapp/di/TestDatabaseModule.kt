package com.nihal.housingapp.di

import android.content.Context
import androidx.room.Room
import com.nihal.housingapp.db.HousesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
class TestDatabaseModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            HousesDatabase::class.java).allowMainThreadQueries().build()
}