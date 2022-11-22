package com.example.buynsellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.buynsellapp.databinding.ActivityHomeBinding
import com.example.buynsellapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    var list = mutableListOf<Data>()
    lateinit var lstView: ListView
    lateinit var adapter: Adapter

    private lateinit var binding : ActivityHomeBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        replaceFragment(Buy())

        lstView = findViewById(R.id.listView)
        lstView.adapter = Adapter(this,R.layout.row,list)

        list.add(Data(R.drawable.img1,"Adidas Shoes","₹ 10000","7995173344"))
        list.add(Data(R.drawable.img2,"Nike Shoes","₹ 15000","8457796123"))
        list.add(Data(R.drawable.img3,"OnePlus 10","₹ 50000","7896543210"))
        list.add(Data(R.drawable.img4,"Iphone 13","₹ 90000","9988776655"))



        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
//                R.id.menuHome -> replaceFragment(Buy())
//                R.id.menuSell -> replaceFragment(Sell())
//                R.id.menuProfile -> replaceFragment(Profile())

                R.id.menuSell -> {
                    val intent = Intent(this,SellPage::class.java)
                    startActivity(intent)
                }


                else ->{

                }
            }
            true
        }
    }

//    private fun replaceFragment(fragment :Fragment){
//
//        val fragManager =supportFragmentManager
//        val fragTras= fragManager.beginTransaction()
//        fragTras.replace(R.id.frmlayout,fragment)
//        fragTras.commit()
//    }
}