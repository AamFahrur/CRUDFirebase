package com.aamfahrur.crudfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = FirebaseFirestore.getInstance()
        val rv = findViewById<RecyclerView>(R.id.recView)
        var pengguna: ArrayList<HashMap<String, String>>
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.layoutManager = layoutManager
        val collection = db.collection("pengguna")
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            startActivity(Intent(this, EditActivity::class.java))
        }

        collection.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("errorsnapshot", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                pengguna = ArrayList()
                for(document in snapshot){
                    val hm: HashMap<String, String> = hashMapOf(
                            "id" to document.id,
                            "name" to document.get("name").toString(),
                            "username" to document.get("username").toString(),
                            "email" to document.get("email").toString(),
                            "address" to document.get("address").toString()
                    )
                    pengguna.add(hm)
                }
                val adapter = RVAdapter(pengguna, this)
                rv.adapter = adapter
            } else {
                Log.d("snapdataerr", "Current data: null")
            }
        }
    }
}