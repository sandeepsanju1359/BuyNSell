package com.example.buynsellapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ResultPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)
        val iv=findViewById<ImageView>(R.id.resimg)
        val resName=findViewById<TextView>(R.id.resName)
        val resPrice=findViewById<TextView>(R.id.resPrice)
        val resContact=findViewById<TextView>(R.id.resContact)

        val bundle:Bundle? =intent.extras
        val img =bundle?.get("image")
        val nm =bundle?.getString("name")
        val pr =bundle?.getString("price")
        val pn =bundle?.getString("contact")

        iv.setImageURI(img as Uri)
        resName.setText(nm)
        resPrice.setText(pr)
        resContact.setText(pn)


    }
}