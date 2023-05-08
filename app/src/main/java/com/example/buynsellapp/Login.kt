package com.example.buynsellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val login = findViewById<Button>(R.id.gstusr)
        val signup =findViewById<TextView>(R.id.tvsignup)
        val btnsin=findViewById<Button>(R.id.btnsignin)
        val uname = findViewById<EditText>(R.id.email)
        val pwd =findViewById<EditText>(R.id.password)

        btnsin.setOnClickListener {
            val un = uname.text.toString()
            val ps =pwd.text.toString()

            auth.signInWithEmailAndPassword(un, ps)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)
                        Toast.makeText(
                            applicationContext,
                            "Login Successful",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Details Mismatch",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

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
