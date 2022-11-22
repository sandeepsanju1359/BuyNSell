package com.example.buynsellapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class SellPage : AppCompatActivity() {
    lateinit var iuri: Uri
    lateinit var reqPer : ActivityResultLauncher<String>
    lateinit var getimg : ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_page)

        val img = findViewById<ImageView>(R.id.Img)
        val btn = findViewById<Button>(R.id.upbtn)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val etName=findViewById<EditText>(R.id.etName)
        val etPrice=findViewById<EditText>(R.id.etPrice)
        val etPhone=findViewById<EditText>(R.id.etPhone)

        val nav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuHome -> {
                    val intent = Intent(this,Home::class.java)
                    startActivity(intent)
                }
                else ->{

                }
            }
            true
        }

        reqPer = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it){
                getimg.launch("image/*")
            }
            else {}

        }
        getimg= registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                if (it != null) {
                    iuri = it
                }
                img.setImageURI(iuri)
            }
        )

        btn.setOnClickListener {
            callPermission()
        }

        btnSubmit.setOnClickListener {
            val intent=Intent(this,ResultPage::class.java)
            var name=etName.text.toString()
            var price=etPrice.text.toString()
            var contact=etPhone.text.toString()
            intent.putExtra("image",iuri)
            intent.putExtra("name",name)
            intent.putExtra("price",price)
            intent.putExtra("contact",contact)
            startActivity(intent)

            Toast.makeText(applicationContext,"Details Uploaded ",Toast.LENGTH_SHORT).show()

        }


    }

    fun callPermission(){
        when{
            ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED ->{
                getimg.launch("image/*")
            }
            else->{
                reqPer.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }
}