package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MisCitas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_citas)

        consultar()

        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //val adapter = CustomAdapter(consultar())

        //recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = adapter

    }

    private fun consultar(): MutableList<Citas>{
        val dates = mutableListOf<Citas>()

        var queue= Volley.newRequestQueue(this.applicationContext)
        val url = "http://192.168.100.6:8888/API_SGTEQ/consultar_citas.php"

        var jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                    var jsonArray=response.getJSONArray("data")
                    for(i in 0 until jsonArray.length() ){

                        var jsonObject=jsonArray.getJSONObject(i)

                        var idCita = jsonObject.getInt("id")
                        var nombreCita = jsonObject.getString("nombre")

                        dates.add(Citas(idCita,nombreCita))

                        //Relacionar con RecyclerView y enviar datos al Adapter.
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                        val adapter = CustomAdapter(dates.reversed())

                        recyclerView.layoutManager = LinearLayoutManager(this)
                        recyclerView.adapter = adapter
                    }
            }, { error ->

            }
        )
        queue.add(jsonObjectRequest)

        return dates
    }
}