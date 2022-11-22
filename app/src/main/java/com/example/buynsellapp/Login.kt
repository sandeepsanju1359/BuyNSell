package com.example.buynsellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<Button>(R.id.gstusr)
        val signup =findViewById<TextView>(R.id.tvsignup)

        login.setOnClickListener {
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext,"logged in as Guest", Toast.LENGTH_LONG).show()

        }

        signup.setOnClickListener {
            val i = Intent(this,SignUp::class.java)
            startActivity(i)
        }
    }
}