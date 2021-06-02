package com.example.androidchallenge.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.androidchallenge.R

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIME_OUT:Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ExchangueRateActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT) // 3000 is the delayed time in milliseconds.
    }
}