package com.example.buynsellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Profile : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val uname = findViewById<TextView>(R.id.userNameTextView)
        val uemail = findViewById<TextView>(R.id.userEmailTextView)
        val sout = findViewById<Button>(R.id.signOutButton)

        val nav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        nav.selectedItemId = R.id.menuProfile

        nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuHome -> {
                    val intent = Intent(this,Home::class.java)
                    startActivity(intent)
                }

                R.id.menuSell ->{
                    val intent = Intent(this,SellPage::class.java)
                    startActivity(intent)
                }


                else -> false
            }
            true
        }

        database = FirebaseDatabase.getInstance()
        databaseRef = database.reference

        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            databaseRef.child("users").child(uid).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("username").value.toString()
                    val email = snapshot.child("email").value.toString()
                    // Update the TextViews with the name and email data
                    uname.text = name
                    uemail.text = email
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error case if needed
                }
            })
        }

        sout.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext,"logged out successfully", Toast.LENGTH_LONG).show()

        }
    }
}