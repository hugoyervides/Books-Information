package com.example.a01382836_androidkotlin_listview_libros_room

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class InformacionLibro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_libro)
        //Declaracion de las varaibles
        var imagen: ImageView = findViewById(R.id.imageView)
        var textTitulo: TextView = findViewById(R.id.textTitulo)
        var textISBN: TextView = findViewById(R.id.textISBN)
        var textFecha: TextView = findViewById(R.id.textFecha)
        var textAuthor: TextView = findViewById(R.id.textAuthor)
        var textURL: TextView = findViewById(R.id.textURL)
        val datos = intent.extras
        //Ver si tenemos datos
        if(datos != null) {
            //conseguir la informacion y ponerla en el texto
            textTitulo.text= datos.getString(MainActivity.BOOK_NAME)
            textISBN.text=datos.getString(MainActivity.BOOK_ISBT)
            textFecha.text=datos.getString(MainActivity.BOOK_DATE)
            textAuthor.text=datos.getString(MainActivity.BOOK_AUTHOR)
            textURL.text=datos.getString(MainActivity.BOOK_URL)
            imagen.setImageResource(getResources().getIdentifier(datos.getString(MainActivity.BOOK_IMAGE), "drawable", this.getPackageName()))
        }else{
            //mostrar error
            Toast.makeText(this, "Error, sin informacion para desplegar!", Toast.LENGTH_SHORT).show()
        }
    }
}