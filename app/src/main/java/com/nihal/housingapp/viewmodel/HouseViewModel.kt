package com.nihal.housingapp.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nihal.housingapp.models.House
import com.nihal.housingapp.models.HousesResponse
import com.nihal.housingapp.repository.AppRepository
import com.nihal.housingapp.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import com.nihal.housingapp.utils.NetworkControl


class HouseViewModel @ViewModelInject constructor(
    private val repository: AppRepository,
    private val networkControl: NetworkControl
): ViewModel(){

    private val _housesResponse = MutableLiveData<Resource<HousesResponse>>()

    val res : LiveData<Resource<HousesResponse>>
        get() = _housesResponse

    init {
        getHouses()
    }

    /**
     * Gets the houses response from the api.
     */
    fun getHouses()  = viewModelScope.launch {
        handleGetHouses()
    }

    /**
     * Handles getting the houses response by posting it to the live data or catching exceptions
     * and handling errors when there is internet connection or any other error.
     */
    private suspend fun handleGetHouses() {
        _housesResponse.value = Resource.loading(null)
        try {
            if(networkControl.isInternetConnected()) {
                repository.getHouses().let {
                    if (it.isSuccessful){
                        _housesResponse.value = Resource.success(it.body())
                    }else{
                        _housesResponse.value = Resource.error(it.errorBody().toString(), null)
                    }
                }
            } else {
                _housesResponse.value = Resource.error("No internet connection.", null)
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> _housesResponse.value = Resource.error("Network Failure.", null)
                else -> _housesResponse.value = Resource.error("Conversion Error Has Occurred.", null)
            }
        }
    }

    /**
     * Inserts a house object in the database.
     * @param house The house object to be inserted.
     */
    fun saveHouse(house: House) = viewModelScope.launch {
        repository.insert(house)
    }

    /**
     * Deletes a house object in the database.
     * @param house The house object to be deleted.
     */
    fun deleteHouse(house: House) = viewModelScope.launch {
        repository.delete(house)
    }

    /**
     * Gets all the houses in the database.
     */
    fun getSavedHouses() = repository.getSavedHouses()


    /**
     * Since the api doesn't have a search query, the searching is done by filtering the list.
     * @param address A city name or zipcode.
     * @return A List with the houses of given address.
     */
    fun searchHouses(address: String): ArrayList<House>? {
        if (_housesResponse.value?.data?.houses == null) {
            return null
        }
        val list: ArrayList<House> = _housesResponse.value?.data?.houses as ArrayList<House>
        val filterList = list.filter {
            it.city.toLowerCase(Locale.ROOT).startsWith(address) ||
                    it.zip.replace(" ", "").toLowerCase(Locale.ROOT).startsWith(address)
        }
        return filterList as ArrayList<House>
    }

}