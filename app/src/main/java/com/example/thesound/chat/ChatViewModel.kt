package com.example.thesound.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thesound.database.chat.Chat
import com.example.thesound.database.chat.ChatDao

class ChatViewModel : ViewModel() {

    private val _chat = MutableLiveData<List<Chat>>()
    val chat : LiveData<List<Chat>> = _chat

    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String> = _msg

    init {
        updateChat()
    }

    private fun updateChat() {
        ChatDao.list()
            .addSnapshotListener { snapshot , error ->
                if(error != null)
                    _msg.value = error.message
                else if (snapshot != null && !snapshot.isEmpty)
                    _chat.value = snapshot.toObjects(Chat::class.java)
                else
                    _msg.value = "Nenhuma mensagem"
            }
        /*ChatDao.list().addOnSuccessListener { querySnapshot ->
            _chat.value = querySnapshot.toObjects(Chat::class.java)
        }.addOnFailureListener {
            _msg.value = it.message
        }*/
    }

    fun insert(mensagem: String) {
        ChatDao.insert(Chat(mensagem,"fabrici@s"))
        //updateChat()
    }

    fun deleteItem(index: Int) {
        try{
            val chat = _chat.value!!.get(index)
            ChatDao.delete(chat)
        }catch (e: Exception){
            _msg.value = e.message
        }
    }
}