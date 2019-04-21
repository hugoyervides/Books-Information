package com.example.a01382836_androidkotlin_listview_libros_room

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONObject

class ActivityData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        //Delclaracion de variables
        var botonAgregarLibro: Button = findViewById(R.id.botonAgregarLibro)
        var botonCodigoQR: Button = findViewById(R.id.botonCodigoQR)
        var editTitulo: EditText = findViewById(R.id.inputTitulo)
        var editISBT: EditText = findViewById(R.id.inputISBT)
        var editFecha: EditText = findViewById(R.id.inputFecha)
        var editAuthor: EditText = findViewById(R.id.inputAuthor)
        var editURL: EditText = findViewById(R.id.inputFecha)
        //Poner listener para agregar el libro
        botonAgregarLibro.setOnClickListener {
            //Conseguir los valores de las casillas y mandarlas al intent
            var data: Intent = Intent()
            //meter los datos
            data.putExtra(MainActivity.BOOK_NAME,editTitulo.text.toString())
            data.putExtra(MainActivity.BOOK_ISBT,editISBT.text.toString())
            data.putExtra(MainActivity.BOOK_DATE,editFecha.text.toString())
            data.putExtra(MainActivity.BOOK_IMAGE,"no_name")
            data.putExtra(MainActivity.BOOK_AUTHOR,editAuthor.text.toString())
            data.putExtra(MainActivity.BOOK_URL,editAuthor.text.toString())
            setResult(1,data)
            finish()
        }
        botonCodigoQR.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        //ver si tenemos contenido en el result
        if(result!=null){
            //ver si el codigo qr contiene data
            if(result.contents == null){
                Toast.makeText(this, "Error, no existen datos en el codigo QR", Toast.LENGTH_SHORT).show()
            }else{
                //sacar los datos del codigo qr
                val jsonString:String= result.contents
                val json: JSONObject = JSONObject(jsonString)
                //Conseguir los valores de las casillas y mandarlas al intent
                val data: Intent = Intent()
                //meter los datos
                data.putExtra(MainActivity.BOOK_NAME,json.getString("title"))
                data.putExtra(MainActivity.BOOK_IMAGE,json.getString("image"))
                data.putExtra(MainActivity.BOOK_ISBT,json.getString("isbn"))
                data.putExtra(MainActivity.BOOK_DATE,json.getString("fecha"))
                data.putExtra(MainActivity.BOOK_AUTHOR,json.getString("author"))
                data.putExtra(MainActivity.BOOK_URL,json.getString("url"))
                setResult(1,data)
                finish()
            }
        }
    }
}