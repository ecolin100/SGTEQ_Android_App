package com.example.proyectofinal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val citas: List<Citas>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    private lateinit var context: Context
    /*
    val titles = arrayOf("Verificación Vehicular", "Solicitud de Licencia", "Renovación de Licencia", "Alta de Vehículo")
    val details = arrayOf("852021", "1122020", "12102020", "12122021")
    val images = intArrayOf(R.drawable.outline_event_white_20,R.drawable.outline_event_white_20,R.drawable.outline_event_white_20,R.drawable.outline_event_white_20)
     */

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        print("AQUI SE DEBERIA DE VER UNA SERIE DE CARACTERES RANDOM: $citas")
        context = viewGroup.context
        var v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val citas = citas.get(i)

        viewHolder.itemTitle.text = citas.nombre
        viewHolder.itemDetail.text = citas.id.toString()
        viewHolder.itemImage.setImageResource(R.drawable.outline_event_white_20)

        with(viewHolder){
            viewHolder.itemTitle.text = citas.nombre
            viewHolder.itemDetail.text = citas.id.toString()
            viewHolder.itemImage.setImageResource(R.drawable.outline_event_white_20)
        }

    }

    override fun getItemCount(): Int {
        return citas.size
    }
}