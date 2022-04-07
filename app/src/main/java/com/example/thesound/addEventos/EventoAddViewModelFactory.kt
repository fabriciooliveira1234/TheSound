package com.example.thesound.addEventos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thesound.database.eventos.EventosDao
import java.lang.IllegalArgumentException

class EventoAddViewModelFactory(
    private val eventosDao: EventosDao) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventoAddViewModel::class.java)) {
            return EventoAddViewModel(eventosDao) as T
        }
        throw IllegalArgumentException("ViewModel incompatível")
    }
}

