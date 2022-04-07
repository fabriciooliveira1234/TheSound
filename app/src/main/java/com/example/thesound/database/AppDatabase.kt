package com.example.thesound.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thesound.database.eventos.Eventos
import com.example.thesound.database.eventos.EventosDao
import com.example.thesound.database.eventos.Genero
import com.example.thesound.database.eventos.GeneroDao


//funções que retornem intâncias das interfaces DAO
@Database(entities = arrayOf(Eventos::class, Genero::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun eventosDao() : EventosDao
    abstract fun generoDao() : GeneroDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context,
                AppDatabase::class.java,"app.db").build()
                INSTANCE = instance
                instance

            }
        }
    }
}