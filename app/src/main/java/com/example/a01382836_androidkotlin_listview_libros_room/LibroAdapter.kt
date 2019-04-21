package com.example.a01382836_androidkotlin_listview_libros_room

import Database.Libro
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.row.view.*

class LibroAdapter (private val context: Context, private val libros: MutableList<Libro>): BaseAdapter(){
    //Delaramos el override de la funcion get view
    override fun getView(position:Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Get viewm for row item
        val rowView = inflater.inflate(R.layout.row, parent, false)

        val libro : Libro = getItem(position) as Libro

        with (rowView){
            imagenLibro.setImageResource(getResources().getIdentifier(libro.image, "drawable", context.getPackageName()))
            //imagenLibro.setImageResource(libro.image)
            bookTitle.text = libro.title
            isbtText.text = libro.id.toString()
            fechaText.text = libro.fecha
        }
        return rowView
    }
    override fun getItem(position: Int): Any {
        return libros.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return libros.size
    }
}