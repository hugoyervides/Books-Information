package com.example.a01382836_androidkotlin_listview_libros_room

import Database.Libro
import Database.LibroDatabase
import android.app.Application
import org.jetbrains.anko.doAsync

// TODO 15: Crear clase que herede de aplicación para inicializar la base de datos,
// solo la primera vez que se ejecuta la aplicación, con el json
class LibroApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        var libros: MutableList<Libro>? = null
        val database = LibroDatabase.getInstance(context = this@LibroApplication)

        // cada acceso a la base de datos se debe realizar asyncrona
        doAsync {
            if (database.libroDao().loadAllLibros().isEmpty()) {
                libros = Libro.populateData(applicationContext)
                database.libroDao().insertAllLibros(libros!!)
            }
        }
    }
}