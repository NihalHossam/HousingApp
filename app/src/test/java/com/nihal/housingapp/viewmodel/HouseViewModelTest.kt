package com.nihal.housingapp.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nihal.housingapp.MainCoroutineRule
import com.nihal.housingapp.models.House
import com.nihal.housingapp.models.HousesResponse
import com.nihal.housingapp.repository.RepositoryTest
import com.nihal.housingapp.utils.NetworkControl
import com.nihal.housingapp.utils.Resource
import com.nihal.housingapp.utils.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class HouseViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private var repository = RepositoryTest()
    private var app = mock(Application::class.java)
    private var networkControl = NetworkControl(app)
    private lateinit var houseViewModel: HouseViewModel
    private val _housesResponse = MutableLiveData<Resource<HousesResponse>>()
    private var isInternetConnected = false
    var testId = 1

    @Before
    fun setup(){
        houseViewModel = HouseViewModel(repository, networkControl)
    }

    @Test
    fun `get houses response with error`() = runBlockingTest{
        repository.setShouldReturnResponseError(true)

        repository.getHouses().let {
            if (it.isSuccessful) {
                _housesResponse.postValue(Resource.success(it.body()))
            } else {
                _housesResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
        assertThat(_housesResponse.value?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `get houses response with no internet connection error`() = runBlockingTest{
        isInternetConnected = false

        if(isInternetConnected) {
            repository.getHouses().let {
                if (it.isSuccessful) {
                    _housesResponse.postValue(Resource.success(it.body()))
                } else {
                    _housesResponse.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        }else{
            _housesResponse.postValue(Resource.error("No internet connection.", null))
        }

        assertThat(_housesResponse.value?.status).isEqualTo(Status.ERROR)
        assertThat(_housesResponse.value?.message).isEqualTo("No internet connection.")
    }

    @Test
    fun `get houses response with success`() = runBlockingTest{
        repository.setShouldReturnResponseError(false)

        repository.getHouses().let {
            if (it.isSuccessful) {
                _housesResponse.postValue(Resource.success(it.body()))
            } else {
                _housesResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
        assertThat(_housesResponse.value?.status).isEqualTo(Status.SUCCESS)
        assertThat(_housesResponse.value?.data?.houses?.get(0)?.id).isEqualTo(1)
        assertThat(_housesResponse.value?.data?.houses?.get(1)?.id).isEqualTo(2)
    }

    @Test
    fun `insert house to mock dao`() = runBlockingTest{
        var id = repository.insert(
            House(testId, ""
            , 100000, 3, 2, "lorem ipsum lorem ipsum",
            "1064GS", "Amsterdam", 52.3, 4.9))
        assertThat(id).isEqualTo(testId)
    }

}