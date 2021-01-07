package com.aamfahrur.crudfirebase

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class RVAdapter(): RecyclerView.Adapter<RVAdapter.RVHolder>() {
    private lateinit var pengguna: ArrayList<HashMap<String, String>>
    private lateinit var activity: AppCompatActivity

    constructor(pengguna: ArrayList<HashMap<String, String>>, activity: AppCompatActivity): this() {
        this.pengguna = pengguna
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return RVHolder(view)
    }

    override fun onBindViewHolder(holder: RVAdapter.RVHolder, position: Int) {
        val id = this.pengguna[position]["id"]
        holder.name.text = pengguna[position]["name"]
        holder.username.text = pengguna[position]["username"]
        holder.itemView.setOnClickListener {
            val intent = Intent(activity, EditActivity::class.java)
            intent.putExtra("pengguna", pengguna[position])
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return pengguna.size
    }
    class RVHolder: RecyclerView.ViewHolder {
        var name: TextView
        var username: TextView
        constructor(itemView: View) : super(itemView) {
            name = itemView.findViewById(R.id.name)
            username = itemView.findViewById(R.id.username)
        }
    }
}