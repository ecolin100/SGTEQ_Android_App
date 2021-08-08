package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class CItaVerificacion : AppCompatActivity() {

    //Declarar e inicializacón de variables
    var servidor = ""
    var idCoche = ""
    var nombre = ""
    var apaterno = ""
    var amaterno = ""

    //Declaración de componentes
    var etTec: EditText?=null
    var etSer: EditText?=null
    var etSem: EditText?=null
    var etMon: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita_verificacion)

        //Incicialización de componentes
        etSer = findViewById(R.id.etSerie)
        etTec = findViewById(R.id.etTecnico)
        etSem = findViewById(R.id.etSemestre)
        etMon = findViewById(R.id.etMonto)

        //Desactivar el EditText nombre del técnico
        etTec?.setKeyListener(null)

        //Poner nombre del técnico
        val objetoIntent: Intent = intent
        servidor = objetoIntent.getStringExtra("servidor").toString()
        nombre = objetoIntent.getStringExtra("nombre").toString()
        apaterno = objetoIntent.getStringExtra("apaterno").toString()
        amaterno = objetoIntent.getStringExtra("amaterno").toString()
        etTec?.text = "${nombre} ${apaterno} ${amaterno}".toEditable()

    }

    //Click en Atender Cita
    fun clickFinalizar(view: View){

        if (etSem?.text.toString().isEmpty() || etSer?.text.toString().isEmpty() || etMon?.text.toString().isEmpty()){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_LONG).show()
        } else {

            val queue = Volley.newRequestQueue(this)
            val url = "$servidor/API_SGTEQ/consultar_serie.php?numero_serie=${etSer?.text.toString()}"

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    idCoche = response.getString("id").toString()
                    //Toast.makeText(this, "Id: $idCoche", Toast.LENGTH_LONG).show()
                    if (idCoche.isNotEmpty())
                        hacerCita()
                }, { error ->
                    //Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                    Toast.makeText(this, "El número de serie ${etSer?.text.toString()} no está registrado", Toast.LENGTH_LONG).show()
                }
            )
            queue.add(jsonObjectRequest)

        }
    }

    fun hacerCita() {
        val url = "$servidor/API_SGTEQ/insertar.php"
        val queue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener <String> { response ->
                Toast.makeText(this, "Cita finalizada exitosamente", Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros.put("semestre",etSem?.text.toString())
                parametros.put("tecnico",etTec?.text.toString())
                parametros.put("monto",etMon?.text.toString())
                parametros.put("auto_id",idCoche)
                return parametros
            }
        }
        queue.add(resultadoPost)
        finish()
    }

    //Funcion para que un elemento se haga de tipo Editable
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}