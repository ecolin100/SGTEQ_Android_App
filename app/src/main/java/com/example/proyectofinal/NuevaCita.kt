package com.example.proyectofinal

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.w3c.dom.Text
import java.util.*

class NuevaCita : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    var servidor = ""
    var hora = 0
    var minuto = 0
    var dia = 0
    var mes = 0
    var año = 0
    var horaObtenida = 0
    var minutoObtenido = 0
    var diaObtenido = 0
    var mesObtenido = 0
    var añoObtenido = 0
    var tramite = ""
    var lugar = ""
    var fechacons = ""
    var horacons = ""
    var id = ""
    var idcita = ""

    //Declaración componentes
    var tvHose: TextView?=null
    var tvFeSe: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_cita)

        //Recibiendo id de la cuenta de la persona
        val objetoIntent: Intent = intent
        servidor = objetoIntent.getStringExtra("servidor").toString()
        id = objetoIntent.getStringExtra("id").toString()

        //Inicialización de componentes
        tvHose = findViewById(R.id.tvHoraSeleccionada)
        tvFeSe = findViewById(R.id.tvFechaSeleccionada)

        //Declaración e incicialización de componentes
        val spTramites = findViewById<Spinner>(R.id.spTramite)
        val spLugares = findViewById<Spinner>(R.id.spLugar)

        //Funcion de Spinner Tramite
        val listaTramites = resources.getStringArray(R.array.tramites)
        val adaptador1 = ArrayAdapter(this,android.R.layout.simple_spinner_item, listaTramites)
        spTramites.adapter = adaptador1

        spTramites.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tramite = listaTramites[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //Funcion de Spinner Lugar
        val listaLugares = resources.getStringArray(R.array.lugares)
        val adaptador2 = ArrayAdapter(this,android.R.layout.simple_spinner_item, listaLugares)
        spLugares.adapter = adaptador2

        spLugares.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                lugar = listaLugares[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        escogerHora()
        escogerFecha()

    }

    private fun obtenerFechayHora(){
        val cal = Calendar.getInstance()
        dia = cal.get(Calendar.DAY_OF_MONTH)
        mes = cal.get(Calendar.MONTH)
        año = cal.get(Calendar.YEAR)

        hora = cal.get(Calendar.HOUR)
        minuto = cal.get(Calendar.MINUTE)
    }

    private fun escogerHora(){
        //Declaración componentes
        val btSeHo = findViewById<Button>(R.id.btSeleccionarHora)
        //Click en Seleccionar Hora
        btSeHo.setOnClickListener(){
            obtenerFechayHora()
            TimePickerDialog(this,this,hora,minuto,true).show()
        }
    }

    private fun escogerFecha(){
        //Declaración componentes
        val btSeFe = findViewById<Button>(R.id.btSeleccionarFecha)
        //Click en Seleccionar Hora
        btSeFe.setOnClickListener(){
            obtenerFechayHora()
            DatePickerDialog(this,this,año,mes,dia).show()
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        horaObtenida = hourOfDay
        minutoObtenido = minute

        if(horaObtenida in 9..16){
            tvHose?.setText("$horaObtenida:$minutoObtenido")
            //Se obtiene la hora para enviar al insert
            horacons = "$horaObtenida:$minutoObtenido"
        } else {
            Toast.makeText(this, "Los horarios de oficina son de 9 a 17 horas", Toast.LENGTH_LONG).show()
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        diaObtenido = dayOfMonth
        mesObtenido = month + 1
        añoObtenido = year

        tvFeSe?.setText("$diaObtenido/$mesObtenido/$añoObtenido")

        //Se obtiene la fecha para enviar al insert
        fechacons = "$añoObtenido/$mesObtenido/$diaObtenido"
    }

    fun verificarVacio():Boolean{
        var listo: Boolean = false
        if (fechacons.length != 0 && horacons.length != 0)
            listo = true
        return listo
    }

    fun clickCrearCita(view: View){
        if (verificarVacio()){
            val queue = Volley.newRequestQueue(this)
            val url = "$servidor/API_SGTEQ/consultar_hora.php?hora=${horacons}&fecha=${fechacons}"

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    idcita = response.getString("id").toString()
                    if (idcita.isNotEmpty())
                        Toast.makeText(this, "El horario seleccionado está ocupado, seleccione uno diferente", Toast.LENGTH_LONG).show()
                }, { error ->
                    insertarCita()
                    //Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                }
            )
            queue.add(jsonObjectRequest)
        }else
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_LONG).show()
    }

    fun insertarCita(){
        val url = "$servidor/API_SGTEQ/insertar_cita.php"
        val queue = Volley.newRequestQueue(this)
        var resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener <String> { response ->
                Toast.makeText(this, "Cita registrada exitosamente", Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros.put("nombre", tramite)
                parametros.put("fecha_cita", fechacons)
                parametros.put("hora_cita", horacons)
                parametros.put("lugar", lugar)
                parametros.put("estatus","Pendiente")
                parametros.put("persona_id", id)
                return parametros
            }
        }
        queue.add(resultadoPost)
        finish()
    }

}