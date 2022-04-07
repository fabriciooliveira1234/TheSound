package com.example.thesound.addEventos

import android.graphics.Bitmap
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesound.database.eventos.Eventos
import com.example.thesound.database.eventos.EventosDao
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kotlin.random.Random

class EventoAddViewModel(
    private val eventosDao: EventosDao) : ViewModel() {

    private var foto : ByteArray? = null

    private var _status =  MutableLiveData<Boolean>()
    var status : LiveData<Boolean> = _status

    private var _msg =  MutableLiveData<String>()
    var msg : LiveData<String> = _msg

    init {
        _status.value = false
        _msg.value = ""
    }


    fun convertFoto(img : Bitmap){
        //Conversao do bitmap para bytearray
        val baos = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        foto = baos.toByteArray()

    }

    fun uploadStorage(id : Long){
        try {
            Firebase.storage.reference.child("eventos/Foto_testes${id}.jpeg")
                .putBytes(foto!!)
                .addOnCompleteListener{ task ->
                    if(task.isComplete) _msg.value = "Imagem enviada com sucesso"
                    else _msg.value = task.exception?.message
                }
        }catch (e : Exception){
            _msg.value = e.message
        }

    }

    fun salvarEvento(genero: String, cidade: String, estado: String) {
        val evento = Eventos(genero,cidade,estado)
        viewModelScope.launch {
            val id = eventosDao.insert(evento)
            if(id != 0L) {
                uploadStorage(id)
                _status.value = true
            }
        }
    }
}