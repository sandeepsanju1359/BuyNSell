package com.example.buynsellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signin =findViewById<TextView>(R.id.tvsignin)
        val login = findViewById<Button>(R.id.gstusr)
        val btnsup = findViewById<Button>(R.id.btnsignup)
        val upname = findViewById<EditText>(R.id.upname)
        val usrnm = findViewById<EditText>(R.id.upemail)
        val pwd = findViewById<EditText>(R.id.uppassword)
        val cpwd = findViewById<EditText>(R.id.confirmPwd)

        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users")

        btnsup.setOnClickListener {
            var uname = usrnm.text.toString()
            var pd = pwd.text.toString()
            var cpd =cpwd.text.toString()
            var name = upname.text.toString()

            if (pd == cpd) {
                auth.createUserWithEmailAndPassword(uname, pd)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val uid = user?.uid ?: ""
                            val userDetails = HashMap<String, String>()
                            userDetails["username"] = name
                            userDetails["email"] = uname
                            databaseRef.child(uid).setValue(userDetails)
                            val i = Intent(this, Login::class.java)
                            startActivity(i)
                            Toast.makeText(
                                applicationContext,
                                "Sign up successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Sign up failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Passwords Mismatch",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        login.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "logged in as Guest", Toast.LENGTH_LONG).show()
        }

        signin.setOnClickListener {
            val i = Intent(this, Login::class.java)
            startActivity(i)
        }
    }
}
