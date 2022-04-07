package com.example.thesound.showApiLyrics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesound.apiservice.Lyrics
import com.example.thesound.apiservice.LyricsApiClient
import kotlinx.coroutines.launch
import java.lang.Exception

class LyricApiShowViewModel : ViewModel() {

    private val _lyric = MutableLiveData<Lyrics>()
    val lyric : LiveData<Lyrics> = _lyric

    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String> = _msg


    fun buscarLyrics(lyricsId: Long) {
        viewModelScope.launch {
            try {
                _lyric.value = LyricsApiClient.endpoint().exibir(lyricsId)
            }catch (e: Exception){_msg.value = e.message}
        }
    }

}