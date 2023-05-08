package com.example.buynsellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashScreen : AppCompatActivity() {
    lateinit var animFade : Animation
    lateinit var animSlide : Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this,SignUp::class.java)
            startActivity(i)
            finish()},5000)

        val logo = findViewById<ImageView>(R.id.logo)
        val title = findViewById<TextView>(R.id.title)
        val des = findViewById<TextView>(R.id.des)

        animFade= AnimationUtils.loadAnimation(applicationContext,R.anim.fade)
        animSlide = AnimationUtils.loadAnimation(applicationContext,R.anim.slide)

        logo.startAnimation(animFade)
        title.startAnimation(animSlide)
        des.startAnimation(animSlide)



    }
}