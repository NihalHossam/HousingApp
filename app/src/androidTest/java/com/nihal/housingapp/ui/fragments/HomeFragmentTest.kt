package com.nihal.housingapp.ui.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.nihal.housingapp.R
import com.nihal.housingapp.adapters.HousesAdapter
import com.nihal.housingapp.models.House
import com.nihal.housingapp.ui.activities.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class HomeFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    val list = arrayListOf<House>(House(1, ""
        , 100000, 3, 2, "lorem ipsum lorem ipsum",
        "1064GS", "Amsterdam", 52.3, 4.9), House(2, ""
        , 500000, 3, 2, "lorem ipsum lorem ipsum",
        "1104GS", "Utrecht", 52.3, 4.9)
    )

    @Test
    fun test_isHomeFragmentInView() {
        onView(withId(R.id.homeFragmentLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isHomeTitleMatching() {
        onView(withId(R.id.homeTextView)).check(matches(withText(containsString("Find Your Dream House"))))
    }

}