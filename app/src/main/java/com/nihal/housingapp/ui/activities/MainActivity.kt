package com.nihal.housingapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nihal.housingapp.R
import com.nihal.housingapp.viewmodel.HouseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


/**
 *  The Activity that is shown after the splash screen, that has a bottom navigation menu to Home, Search and Saved Fragments.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val houseViewModel: HouseViewModel by viewModels()
    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController!!)
    }

}