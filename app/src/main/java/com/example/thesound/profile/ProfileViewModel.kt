package com.example.thesound.profile

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thesound.model.Profile
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class ProfileViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status : LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String> = _msg

    private var foto : ByteArray? = null
    private var profileSave : Boolean = false
    private var fotoSave : Boolean = false


    fun savePerfil(endereco: String, idade: String, music: String) {
        //Firestore : salvar endereco,idade e music no odcumento users/uid
        val uid = Firebase.auth.currentUser!!.uid
        val idade_int = idade.toInt()
        salvarPerfilDoUsuario(uid, endereco, idade_int, music)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    profileSave = true
                    if(fotoSave)
                        _status.value = true
                } else {
                    _msg.value = it.exception?.message
                }
            }
        //Storage : Salvar a foto em users/fotos/uid
        salvarFotoDoUsuario(uid)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    fotoSave = true
                    if(profileSave)
                        _status.value = true
                }else{
                    _msg.value = it.exception?.message
                }
            }
    }

    private fun salvarFotoDoUsuario(uid: String) = Firebase
        .storage
        .reference
        .child("users/fotos/${uid}")
        .putBytes(foto!!)

    private fun salvarPerfilDoUsuario(
        uid: String,
        endereco: String,
        idade_int: Int,
        music: String): Task<Void> {
        return Firebase
            .firestore
            .collection("users")
            .document(uid)
            .set(Profile(endereco, idade_int, music))
    }

    fun setFoto(img : Bitmap){
        //Conversao do bitmap para bytearray
        val baos = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        foto = baos.toByteArray()

    }

}