package com.nihal.housingapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nihal.housingapp.db.HouseDao
import com.nihal.housingapp.models.House
import com.nihal.housingapp.models.HousesResponse
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Rule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Response

/**
 * A test repository to be used by the viewmodel in testcases.
 * The room database is tested in androidTest. A Mock is just used in here.
 */
class RepositoryTest: AppRepository{

    private val house1 = House(1, ""
        , 100000, 3, 2, "lorem ipsum lorem ipsum",
        "1064GS", "Amsterdam", 52.3, 4.9)

    private val house2 = House(2, ""
        , 100000, 3, 2, "lorem ipsum lorem ipsum",
        "1064GS", "Amsterdam", 52.3, 4.9)


    private val houseItems = mutableListOf<House>()
    private val allHouses = MutableLiveData<List<House>>(houseItems)
    private var houseDao = mock(HouseDao::class.java)

    private var shouldReturnResponseError = false

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    fun setShouldReturnResponseError(value: Boolean){
        shouldReturnResponseError = value
    }

    private fun refreshLiveData(){
        allHouses.postValue(houseItems)
    }

    override suspend fun insert(house: House): Long {
        `when`(houseDao.insertHouse(house)).thenReturn(house.id.toLong())
        assertEquals(houseDao.insertHouse(house), house.id.toLong())
        houseItems.add(house)
        refreshLiveData()
        return houseDao.insertHouse(house)
    }

    override suspend fun delete(house: House) {
        return
    }

    override fun getSavedHouses(): LiveData<List<House>> {
        assertNotNull(houseDao)
        return allHouses
    }

    override suspend fun getHouses(): Response<HousesResponse> {
        return if (shouldReturnResponseError){
            Response.error(404, ResponseBody.create("application/json".toMediaTypeOrNull(), "{\"key\":[\"somestuff\"]}"))
        }else{
            houseItems.clear()
            houseItems.add(house1)
            houseItems.add(house2)
            Response.success(HousesResponse(houseItems))
        }
    }



}