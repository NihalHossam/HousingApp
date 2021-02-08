package com.nihal.housingapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.nihal.housingapp.getOrAwaitValue
import com.nihal.housingapp.models.House
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class HouseDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: HousesDatabase
    private lateinit var dao: HouseDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao = database.getHouseDao()
    }

    @After
    fun clear(){
        database.close()
    }

    @Test
    fun insertHouse() = runBlockingTest {
        val houseItem = House(1, "https://twiggsrealty.com/wp-content/uploads/2020/03/home03.jpg"
            , 100000, 3, 2, "lorem ipsum lorem ipsum",
            "1064GS", "Amsterdam", 52.3, 4.9)
        dao.insertHouse(houseItem)
        val allHouses = dao.getAllHouses().getOrAwaitValue()
        assertThat(allHouses).contains(houseItem)
    }

    @Test
    fun deleteHouse() = runBlockingTest {
        val houseItem = House(1, "https://twiggsrealty.com/wp-content/uploads/2020/03/home03.jpg"
            , 100000, 3, 2, "lorem ipsum lorem ipsum",
            "1064GS", "Amsterdam", 52.3, 4.9)
        dao.insertHouse(houseItem)
        dao.deleteHouse(houseItem)
        val allHouses = dao.getAllHouses().getOrAwaitValue()
        assertThat(allHouses).doesNotContain(houseItem)
    }

}