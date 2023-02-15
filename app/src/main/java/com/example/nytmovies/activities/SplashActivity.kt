package com.example.nytmovies.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nytmovies.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //rotateAnimation()


        window.setFlags(
            WindowManager.LayoutParams.FLAGS_CHANGED,
            WindowManager.LayoutParams.FLAGS_CHANGED
        )
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LaunchActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.right, R.anim.left)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }

//    private fun rotateAnimation() {
//        val textview = findViewById<TextView>(R.id.text)
//
//        val animation = AnimationUtils.loadAnimation(this, R.anim.ani)
//        textview.startAnimation(animation)
//
//
//    }

}