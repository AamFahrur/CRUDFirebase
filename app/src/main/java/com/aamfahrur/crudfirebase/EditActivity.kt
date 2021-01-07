package com.aamfahrur.crudfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val db = FirebaseFirestore.getInstance()
        val collection = db.collection("pengguna")
        val pserial = intent.getSerializableExtra("pengguna")

        val submit = findViewById<Button>(R.id.submitEdit)
        val delete = findViewById<Button>(R.id.deleteEdit)
        val username = findViewById<TextInputEditText>(R.id.usernameEdit)
        val fullname = findViewById<TextInputEditText>(R.id.fullnameEdit)
        val email = findViewById<TextInputEditText>(R.id.emailEdit)
        val address = findViewById<TextInputEditText>(R.id.addressEdit)

        val pengguna = if(pserial != null){
            delete.visibility = View.VISIBLE
            pserial as HashMap<*,*>
        }else{
            null
        }

        if(pengguna != null){
            username.setText(pengguna["username"].toString())
            fullname.setText(pengguna["name"].toString())
            email.setText(pengguna["email"].toString())
            address.setText(pengguna["address"].toString())
        }

        delete.setOnClickListener {
            collection.document(pengguna!!["id"].toString()).delete().addOnSuccessListener {
                Toast.makeText(this, "Success delete data with id ${pengguna["id"]}", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }

        submit.setOnClickListener {
            val data = hashMapOf(
                "username" to username.text.toString(),
                "name" to fullname.text.toString(),
                "email" to email.text.toString(),
                "address" to address.text.toString()
            )
            if(pengguna == null){
                collection.add(data).addOnSuccessListener {documentReference ->
                    Toast.makeText(this, "Success add new data with id ${documentReference.id}", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }else{
                @Suppress("UNCHECKED_CAST")
                collection.document(pengguna["id"].toString()).update(data as Map<String, Any>).addOnSuccessListener {
                    Toast.makeText(this, "Success update data with id ${pengguna["id"]}", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }
    }
}