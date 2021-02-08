package com.nihal.housingapp.di

import com.nihal.housingapp.api.ApiHelper
import com.nihal.housingapp.api.ApiHelperImpl
import com.nihal.housingapp.api.ApiService
import com.nihal.housingapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * To tell Hilt how to provide instances of this type,
 * to be used for dependency injection.
 */
@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule{

    /**
     * Create the Retrofit singleton that will instantiate once using the configured values.
     * @return the created retrofit instance.
     * */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    /**
     * Validates the configuration of the methods in the APIService interface.
     * @param retrofit The created retrofit instance.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    /**
     * Provides the api helper to repository.
     * @return The api helper object that will be used for constructor injection in repository.
     */
    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}