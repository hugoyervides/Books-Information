package Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

// @Database, Serves as the database holder an is the main accespoint to your relational data.
@Database(entities = arrayOf(Libro::class), version = 1, exportSchema = false)

abstract class LibroDatabase : RoomDatabase() {

    // contains all your DAOs as abstract methods
    abstract fun libroDao(): LibroDao

    // using singleton object to handle the database and only one instance exists
    companion object {

        private val DATABASE_NAME = "LibroDB.db"
        private var dbInstance: LibroDatabase? = null  // instancia de la bd - singleton

        /**
         * getInstance. obtiene la instancia de la base de datos, en caso de no existir la crea
         */
        @Synchronized
        fun getInstance(context: Context): LibroDatabase =
            dbInstance ?: buildDatabase(context).also { dbInstance = it}

        /**
         * buildDatabase. crea la base de datos con el nomnbre  especificado
         */
        private fun buildDatabase(context: Context): LibroDatabase = Room.databaseBuilder(context,
            LibroDatabase::class.java,
            DATABASE_NAME)
            .build()
    }
}
