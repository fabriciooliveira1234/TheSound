package com.example.thesound.eventos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thesound.database.eventos.EventosDao

class EventosViewModelFactory(private val eventosDao: EventosDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EventosViewModel::class.java)){
            return EventosViewModel(eventosDao) as T
        }
        throw IllegalArgumentException("Classe não conhecida")
    }
}