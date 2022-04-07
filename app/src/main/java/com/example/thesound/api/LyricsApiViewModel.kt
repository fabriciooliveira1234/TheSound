package com.example.thesound.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesound.apiservice.Lyrics
import com.example.thesound.apiservice.LyricsApiClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LyricsApiViewModel : ViewModel() {

    private val _lyrics = MutableLiveData<List<Lyrics>>()
    val lyrics : LiveData<List<Lyrics>> = _lyrics

    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String> = _msg

    private val lyricsService = LyricsApiClient.endpoint()

    init {
        viewModelScope.launch {
            try {
                _lyrics.value = lyricsService.list()
            }catch (e: Exception){
                _msg.value = e.message
            }

        }
           /* .enqueue(object : Callback<List<Lyrics>>{
            override fun onResponse(call: Call<List<Lyrics>>, response: Response<List<Lyrics>>) {
                _lyrics.value = response.body()
            }

            override fun onFailure(call: Call<List<Lyrics>>, t: Throwable) {
                _msg.value = t.message
            }

        })*/
    }

    fun filtrarLista(filtro: String) {
        viewModelScope.launch {
            try {
                _lyrics.value = lyricsService.listComFiltro(filtro)
            }catch (e: Exception){
                _msg.value = e.message
            }

        }
    }
}