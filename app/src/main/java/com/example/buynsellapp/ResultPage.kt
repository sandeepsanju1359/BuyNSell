package com.example.buynsellapp

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.net.URI

class ResultPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)
        val iv=findViewById<ImageView>(R.id.resimg)
        val resName=findViewById<TextView>(R.id.resName)
        val resPrice=findViewById<TextView>(R.id.resPrice)
        val resContact=findViewById<TextView>(R.id.resContact)
        val resDes = findViewById<TextView>(R.id.resDes)
        val btnBuy = findViewById<Button>(R.id.btnBuy)

        val nav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        nav.selectedItemId = R.id.menuSell

        nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuHome -> {
                    val intent = Intent(this,Home::class.java)
                    startActivity(intent)
                }

                R.id.menuProfile ->{
                    val intent = Intent(this,Profile::class.java)
                    startActivity(intent)
                }


                else -> false
            }
            true
        }


        val imgUri = intent.getParcelableExtra<Uri>("img")
        Glide.with(this).load(imgUri).into(iv)

        resName.text = intent.getStringExtra("name")
        resPrice.text = intent.getStringExtra("price")
        resContact.text = intent.getStringExtra("contact")
        resDes.text = intent.getStringExtra("des")

        btnBuy.setOnClickListener {
            val options = arrayOf<CharSequence>("Make a call", "Send a message")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose an option")
            builder.setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${resContact.text}")
                        this.startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(Intent.ACTION_SENDTO)
                        val message = "Hi, I am interested in buying your ${resName.text}. I want to know more about the product." +
                                " Please let me know if it is still available."
                        intent.data = Uri.parse("smsto:${resContact.text}")
                        intent.putExtra("sms_body",message)
                        this.startActivity(intent)
                    }
                }
            }
            builder.show()
        }

    }
}