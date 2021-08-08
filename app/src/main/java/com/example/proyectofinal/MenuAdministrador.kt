package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MenuAdministrador : AppCompatActivity() {

    var servidor = ""
    var id = ""
    var name = ""
    var nombre = ""
    var apaterno = ""
    var amaterno = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_administrador)

        //Declaración componentes
        val tvSaludo = findViewById<TextView>(R.id.tvSaludo2)
        val btSalir = findViewById<Button>(R.id.btCerrarSesion)

        //Poner nombre
        val objetoIntent: Intent = intent
        servidor = objetoIntent.getStringExtra("servidor").toString()
        nombre = objetoIntent.getStringExtra("nombre").toString()
        apaterno = objetoIntent.getStringExtra("apellidop").toString()
        amaterno = objetoIntent.getStringExtra("apellidom").toString()
        tvSaludo.text = "¡Bienvenido $nombre!"

        //Click en Salir
        btSalir.setOnClickListener(){
            finish()
        }
    }

    //Click en Atender Cita
    fun clickAtenderCita(view: View){
        val etcita = findViewById<EditText>(R.id.etFolio)
        val foliocita = etcita.text.toString()

        if (foliocita.isEmpty()){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_LONG).show()
        } else {
            val queue = Volley.newRequestQueue(this)
            val url = "$servidor/API_SGTEQ/consultar_cita.php?id=$foliocita"

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    id = response.getString("id")
                    name = response.getString("nombre")

                    if (name == "Verificación Vehicular" ){
                        intent = Intent(this, CItaVerificacion::class.java)
                        intent.putExtra("servidor", servidor)
                        intent.putExtra("nombre", nombre)
                        intent.putExtra("apaterno", apaterno)
                        intent.putExtra("amaterno", amaterno)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "El tipo de esta cita aún no es soportado", Toast.LENGTH_LONG).show()
                    }

                }, { error ->
                    Toast.makeText(this, "Cita no encontrada, verifique sus datos", Toast.LENGTH_LONG).show()
                    //Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                }
            )
            queue.add(jsonObjectRequest)
        }

    }
}