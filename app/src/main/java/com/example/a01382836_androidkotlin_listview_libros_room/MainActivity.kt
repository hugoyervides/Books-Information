package com.example.a01382836_androidkotlin_listview_libros_room

import Database.Libro
import Database.LibroDatabase
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    // TODO 17: definir función qye cargue los datos de los niños almacenados en la base de datos
    val emptyMutableList:MutableList<Libro> = mutableListOf<Libro>()

    // TODO 18: obtener la instancia de la base de datos
    var instanceDatabase: LibroDatabase? = null

    var libros: MutableList<Libro> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Declaracion del boton
        var botonAgregar: FloatingActionButton = findViewById(R.id.botonFlotante)
        //Conseguir instancia de la base de datos
        instanceDatabase = LibroDatabase.getInstance(this@MainActivity)
        //Agregar listener al boton
        botonAgregar.setOnClickListener {
            var intent: Intent = Intent(this, ActivityData::class.java)
            startActivityForResult(intent,1)
        }
        //Cargar todos los libros
        loadAllLibrosDB()
    }
    private fun loadAllLibrosDB(){
        doAsync {
            libros = instanceDatabase?.libroDao()?.loadAllLibros() ?: emptyMutableList
            uiThread {
                val adapter = LibroAdapter(applicationContext, libros)
                lista_libros.adapter = adapter
                adapter.notifyDataSetChanged()
                //Agregar listener a los libros
                lista_libros.setOnItemClickListener{adapterView, view, position, l ->
                    displayBookInfo(libros.get(position))
                }
            }
        }
    }
    private fun displayBookInfo(libro:Libro){
        //Crear intent
        var intent: Intent =Intent(this, InformacionLibro::class.java)
        //Poner la informacion del libro
        intent.putExtra(BOOK_NAME,libro.title)
        intent.putExtra(BOOK_IMAGE,libro.image)
        intent.putExtra(BOOK_AUTHOR,libro.author)
        intent.putExtra(BOOK_DATE,libro.fecha)
        intent.putExtra(BOOK_ISBT,libro.id.toString())
        intent.putExtra(BOOK_URL,libro.url)
        startActivity(intent)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Ver si nos llegaron datos
        if(data!=null){
            doAsync {
                //Parsar los datos en un libro
                val libroNuevo:Libro = Libro(data.getStringExtra(BOOK_NAME),
                    data.getStringExtra(BOOK_IMAGE),
                    data.getStringExtra(BOOK_DATE),
                    data.getStringExtra(BOOK_AUTHOR),
                    data.getStringExtra(BOOK_URL),
                    data.getStringExtra(BOOK_ISBT).toInt())
                instanceDatabase?.libroDao()?.inserLibro(libroNuevo)
                libros = instanceDatabase?.libroDao()?.loadAllLibros() ?: emptyMutableList
                uiThread{
                    val adapter = LibroAdapter(applicationContext, libros)
                    lista_libros.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
        }
        else{
            //Desplegar toast indicando que no llego data para agregar
            Toast.makeText(this, "Sin datos!", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        val BOOK="libro"
        val BOOK_NAME="nombreLibro"
        val BOOK_ISBT="isbtLibro"
        val BOOK_DATE="fechaLibro"
        val BOOK_IMAGE="imagenLibro"
        val BOOK_AUTHOR="autor"
        val BOOK_URL="url"
        val LIBROS="books"
    }
}