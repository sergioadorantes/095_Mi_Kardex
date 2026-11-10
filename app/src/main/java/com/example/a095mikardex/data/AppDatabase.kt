package com.example.mikardex.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Materia::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun materiaDao(): MateriaDao

    companion object {
        @Volatile
        private var instancia: AppDatabase? = null

        fun obtener(context: Context): AppDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kardex.db"
                ).build().also { instancia = it }
            }
    }
}
