package Database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface LibroDao {

    // @Query, realiza una consulta en la base de datos
    @Query("SELECT * FROM Libro ORDER BY title")
    fun loadAllLibros():MutableList<Libro>

    // @Insert, inserta uno u vairos elementos en la base de datos
    @Insert
    fun insertAllLibros(libroList: List<Libro>)

    @Insert
    fun inserLibro(libro:Libro)
}