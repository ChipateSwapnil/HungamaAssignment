package com.hungama.android.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.hungama.android.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launchActivityWithDelay()
    }

    private fun launchActivityWithDelay() {
        //handler class to delay the call : to launch app after 2 sec
        Handler(Looper.getMainLooper()).postDelayed({
           val intent = Intent(this,MovieListingActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}