package Database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.Context
import android.util.Log
import org.jetbrains.annotations.NotNull
import org.json.JSONArray
import org.json.JSONObject

//data class Libro (var title:String, var image:String, var isbn:String, var fecha:String, var author:String, var url:String):Serializable
@Entity(tableName = "Libro")
data class Libro (@ColumnInfo(name = "title") @NotNull val title:String,
                  @ColumnInfo(name = "image") val image:String,
                  @ColumnInfo(name = "fecha") val fecha:String,
                  @ColumnInfo(name = "author") val author:String,
                  @ColumnInfo(name = "url") val url:String,
                  @ColumnInfo(name="_id") @PrimaryKey(autoGenerate = true) var id: Int = 0){

    // TODO 5: definir función que carga los datos de los niños de un JSON
    companion object {
        const val JSON_FILE_NAME: String = "libros.json"
        fun populateData(context: Context): MutableList<Libro> = loadJsonLibros(JSON_FILE_NAME, context)
    }
}
//Funcion que regresa una lista mutable de libros con un json en string
fun loadJsonLibros(fileName:String,context: Context):MutableList<Libro>{

    var jsonString= loadJsonFromAsset("libros.json",context)
    //Pasar el Json en formato string a JSON
    var libros:MutableList<Libro> = mutableListOf()
    val json: JSONObject = JSONObject(jsonString)
    val jsonLibros: JSONArray = json.getJSONArray("books")
    //Recorrer el json para regresar la lista mutable llena
    for(i in 0 until jsonLibros.length()){
        libros.add(
            Libro(
                jsonLibros.getJSONObject(i).getString("title"),
                jsonLibros.getJSONObject(i).getString("image"),
                jsonLibros.getJSONObject(i).getString("fecha"),
                jsonLibros.getJSONObject(i).getString("author"),
                jsonLibros.getJSONObject(i).getString("url"),
                jsonLibros.getJSONObject(i).getInt("isbn")
            )
        )
    }
    return libros
}
//Funcion para cargar el archivo json
private fun loadJsonFromAsset(fileName: String, context: Context): String =
    (context.assets.open(fileName) ?: throw RuntimeException("Cannot open file: $fileName")).bufferedReader().use { it.readText() }