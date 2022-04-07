package com.example.thesound

import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.example.thesound.apiservice.Endpoint
import com.example.thesound.database.AppDatabase
import com.example.thesound.database.eventos.Eventos
import com.example.thesound.database.eventos.Genero
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.gson.JsonObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //private var storage : StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}

        /*Storage Firebase
//Apontando para o diretório raiz do storage c:
val storage = Firebase.storage.reference

//var imagem = storage.child("download.jpg")
var imagem = storage.child("imagens/download.jpg")

Log.d("Google Cloud Storage", imagem.path)
Log.d("Google Cloud Storage", imagem.name)

//Download- Get
var task = imagem.getBytes(1024 * 1024) //disparo da requisição*/

        /*Armazenamento Externo
        val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "teste.fabricio")
        Log.d("FileExternal", file.exists().toString())*/

        /*Instanciamento do AppDatabase
        val appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
        var eventosDao = appDatabase.eventosDao()*/

        /*zCorroutines
        //Essa parte do código não pode ser executada na thread principal então será colocado em segundo plano usando courrotine
        //para iniciar a chamada em segundo plano devemos definir o global scope
        GlobalScope.launch {
            val eventosId = eventosDao.insert(Eventos("Sp", "Botucatu",))
            appDatabase.generoDao().insert(Genero("Rock", eventosId))
            //appDatabase.eventosDao().insert(Eventos(1238,"rock","RJ", "Rio de Janeiro"))
        }*/

