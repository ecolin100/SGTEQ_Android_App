package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MenuUsuario : AppCompatActivity() {

    var servidor = ""
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_usuario)

        //Declaración componentes
        val tvSaludo = findViewById<TextView>(R.id.tvSaludo)
        val btMC = findViewById<Button>(R.id.btMisCitas)
        val btNC = findViewById<Button>(R.id.btNuevaCita)
        val btSalir = findViewById<Button>(R.id.btSalir)

        //Poner nombre
        val objetoIntent: Intent = intent
        val nombre = objetoIntent.getStringExtra("nombre")
        servidor = objetoIntent.getStringExtra("servidor").toString()
        id = objetoIntent.getStringExtra("id").toString()
        tvSaludo.text = "¡Bienvenido $nombre!"

        //Click en Mis Citas
        btMC.setOnClickListener(){
            intent = Intent(this, MisCitas::class.java)
            intent.putExtra("servidor", servidor)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        //Click en Nueva Cita
        btNC.setOnClickListener(){
            intent = Intent(this, NuevaCita::class.java)
            intent.putExtra("servidor", servidor)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        //Click en Salir
        btSalir.setOnClickListener(){
            finish()
        }

    }
}