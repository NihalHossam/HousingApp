package com.nihal.housingapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nihal.housingapp.R

/**
 * An Activity that shows a splash screen on the start of the app.
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}