package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    /* En la variable "servidor" sustituir la dirección ip de su servidor
    Por ejemplo: http://192.168.100.6:8888
    En caso de que se use el puerto 80 para la conexión al servidor Apache se puede omitir el mismo
    Por ejemplo: http://192.168.100.6 */
    var servidor = "http://192.168.100.6:8888"

    var id = ""
    var name = ""
    var lname1 = ""
    var lname2 = ""
    var mail = ""
    var pass = ""
    var rol = ""

    var swmodo: SwitchMaterial ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swmodo = findViewById(R.id.swCambiarModo)
    }

    fun clickSwitch(view: View){
        if (swmodo?.isChecked == true){
            setColorMode(0)
        } else {
            setColorMode(1)
        }
    }

    //Click en Ingresar
    fun clickIngresar(view:View){
        //Declaración e incicialización de componentes
        val etCorr = findViewById<EditText>(R.id.etCorreo)
        val etCon = findViewById<EditText>(R.id.etContrasena)

        val correo = etCorr?.text.toString()
        val contrasena = etCon?.text.toString()

        if (correo.length == 0 || contrasena.length == 0)
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_LONG).show()
        else{
            val queue = Volley.newRequestQueue(this)
            val url = "$servidor/API_SGTEQ/consultar.php?correo=$correo&contrasenia=$contrasena"

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    id = response.getString("id")
                    name = response.getString("nombre")
                    lname1 = response.getString("apellido_paterno")
                    lname2 = response.getString("apellido_materno")
                    mail = response.getString("correo")
                    pass = response.getString("contrasenia")
                    rol = response.getString("rol")

                    if (rol == "0" ){
                        intent = Intent(this, MenuUsuario::class.java)
                        intent.putExtra("servidor", servidor)
                        intent.putExtra("id", id)
                        intent.putExtra("nombre", name)
                        startActivity(intent)
                    } else if (rol == "1"){
                        intent = Intent(this, MenuAdministrador::class.java)
                        intent.putExtra("servidor", servidor)
                        intent.putExtra("id", id)
                        intent.putExtra("nombre", name)
                        intent.putExtra("apellidop", lname1)
                        intent.putExtra("apellidom", lname2)
                        startActivity(intent)
                    }

                }, { error ->
                    if (!mail.equals(correo) && !pass.equals(contrasena))
                        Toast.makeText(this, "Cuenta no encontrada, verifique sus datos", Toast.LENGTH_LONG).show()
                    //Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                }
            )
            queue.add(jsonObjectRequest)
        }

    }

    //Click en Información
    fun clickInformacion(view: View){
        var modoin: Int

        if (swmodo?.isChecked == true){
            modoin = 0
        } else {
            modoin = 1
        }
        //Toast.makeText(this, modoin.toString(), Toast.LENGTH_SHORT).show()
        intent = Intent(this, Acercade::class.java)
        intent.putExtra("modo", modoin.toString())
        startActivity(intent)
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