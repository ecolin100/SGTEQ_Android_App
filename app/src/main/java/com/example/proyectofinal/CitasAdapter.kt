package com.example.proyectofinal

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitasAdapter(private val citas: List<Citas>) : RecyclerView.Adapter<CitasAdapter.ViewHolder>(){

    private lateinit var context: Context

inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
    //val binding = ItemCitaBinding.bind(view)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}