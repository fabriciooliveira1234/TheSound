package com.example.thesound.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class SignInViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status : LiveData<Boolean> = _status

    var hasFoto : Boolean = false
    var hasProfile : Boolean = false

    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String> = _msg

    init {
        _msg.value = ""
        _status.value = false

    }

    fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
            if(it.user != null) {
                //Verificar a existência de foto através do storage
                verificarFotodoUsuario(it.user!!)
                //Verificar a existência de dados no Firestore
                verificarPerfilDoUsuario(it.user!!)

                _status.value = true
            }
        }.addOnFailureListener {
            _msg.value = it.message
        }
    }

    private fun verificarPerfilDoUsuario(user: FirebaseUser) {
        Firebase
            .firestore
            .collection("users")
            .document(user.uid)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists())
                    hasProfile = true
            }
    }

    private fun verificarFotodoUsuario(user: FirebaseUser) {
        Firebase
            .storage
            .reference
            .child("users/fotos/${user.uid}")
            .getBytes((1024 * 1024).toLong())
            .addOnCompleteListener { storage ->
                if (storage.isSuccessful && storage.result != null)
                    hasFoto = true
            }
    }

}