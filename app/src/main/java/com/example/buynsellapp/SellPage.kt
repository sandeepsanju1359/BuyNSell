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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*

class SellPage : AppCompatActivity() {
    private var iuri: Uri ?= null
    lateinit var reqPer : ActivityResultLauncher<String>
    lateinit var getimg : ActivityResultLauncher<String>
    private lateinit var sellItemsRef: DatabaseReference
    private lateinit var imageRef: StorageReference
    lateinit var etName : EditText
    lateinit var etPrice : EditText
    lateinit var etPhone : EditText
    lateinit var etDes : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_page)

        etName=findViewById<EditText>(R.id.etName)
        etPrice=findViewById<EditText>(R.id.etPrice)
        etPhone=findViewById<EditText>(R.id.etPhone)
        etDes = findViewById<EditText>(R.id.etAge)


        val storage = Firebase.storage
        val database = Firebase.database.reference
        val storageRef = storage.reference
         sellItemsRef = database.child("SellItems")
        val filename = etName.text.toString()


        val img = findViewById<ImageView>(R.id.Img)
        val btn = findViewById<Button>(R.id.upbtn)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)



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


            Toast.makeText(applicationContext,"Details Uploaded ",Toast.LENGTH_SHORT).show()

            imageRef = storageRef.child("images/$filename.jpg")


            imageRef.putFile(iuri!!)
                .addOnSuccessListener {
                    // Get the download URL for the image
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Save the details to the database
                        val item = HashMap<String, String>()
                        item["name"] = etName.text.toString()
                        item["price"] = etPrice.text.toString()
                        item["contact"] = etPhone.text.toString()
                        item["description"]=etDes.text.toString()
                        item["image"] = uri.toString() // Save the download URL to the 'image' property

                        sellItemsRef.push().setValue(item)
                            .addOnSuccessListener {
                                Toast.makeText(applicationContext, "Details Uploaded ", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
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