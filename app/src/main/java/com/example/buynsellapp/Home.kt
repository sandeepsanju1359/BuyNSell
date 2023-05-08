package com.example.buynsellapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class Home : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private var itemList = mutableListOf<Data>()

    private lateinit var sellItemsRef: DatabaseReference
    private lateinit var database: FirebaseDatabase


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(itemList)
        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance()
        sellItemsRef = database.getReference("SellItems")

        sellItemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                itemList.clear()
                for (snapshot in dataSnapshot.children) {
                    val item = snapshot.getValue(Item::class.java)
                    if (item != null) {
                        itemList.add(Data(item.image, item.name, "₹ " + item.price,item.contact, item.description))
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuSell -> {
                    val intent = Intent(this, SellPage::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menuProfile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}




//class Home : AppCompatActivity() {
//    lateinit var lstView: ListView
//    lateinit var adapter: Adapter
//    var list = mutableListOf<Data>()
//
//    private lateinit var sellItemsRef : DatabaseReference
//    private lateinit var database : FirebaseDatabase
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//
//        lstView = findViewById(R.id.listView)
//        adapter = Adapter(this, R.layout.row, list)
//        lstView.adapter = adapter
//
//        database = FirebaseDatabase.getInstance()
//        sellItemsRef = database.getReference("SellItems")
//
//        sellItemsRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                list.clear()
//                for (snapshot in dataSnapshot.children) {
//                    val item = snapshot.getValue(Item::class.java)
//                    if (item != null) {
//                        list.add(Data(item.image, item.name, "₹ "+ item.price, item.contact))
//                    }
//                }
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Toast.makeText(applicationContext, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        bottomNavigationView.setOnItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.menuSell -> {
//                    val intent = Intent(this, SellPage::class.java)
//                    startActivity(intent)
//                    true
//                }
//                R.id.menuCart -> {
//                    // Handle cart action
//                    true
//                }
//                R.id.menuProfile -> {
//                    val intent = Intent(this, Profile::class.java)
//                    startActivity(intent)
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//}
