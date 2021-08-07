package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class Acercade : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acercade)

        val objetoIntent: Intent = intent
        var modo = objetoIntent.getStringExtra("modo").toString()
        //Toast.makeText(this, modo, Toast.LENGTH_SHORT).show()
        establecerModo(modo)

    }

    fun establecerModo(modo: String){
        //Toast.makeText(this, "Recibiendo modo $modo", Toast.LENGTH_SHORT).show()
        if (modo == "0"){
            setColorMode(0)
        } else {
            setColorMode(1)
        }
    }

    fun clickRegresar(view: View){
        finish()
    }

    fun setColorMode(modo:Int):Int {
        if (modo == 0){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO;
        }
        return modo
    }
}