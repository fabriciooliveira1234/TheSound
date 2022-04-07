package com.example.thesound.eventos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesound.database.eventos.Eventos
import com.example.thesound.database.eventos.EventosDao
import com.example.thesound.database.eventos.EventoseGenero
import kotlinx.coroutines.launch

class EventosViewModel(private val eventosDao: EventosDao) : ViewModel() {
    private val _eventos = MutableLiveData<List<EventoseGenero>>()

    val eventos :  LiveData<List<EventoseGenero>> = _eventos

    init {
        viewModelScope.launch {
            _eventos.value = eventosDao.listWithAutores()
        }
    }
}