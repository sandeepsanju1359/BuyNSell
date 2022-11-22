package com.example.buynsellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signin =findViewById<TextView>(R.id.tvsignin)
        val login = findViewById<Button>(R.id.gstusr)

        login.setOnClickListener {
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext,"logged in as Guest",Toast.LENGTH_LONG).show()
        }

        signin.setOnClickListener {
            val i = Intent(this,Login::class.java)
            startActivity(i)
        }
    }
}