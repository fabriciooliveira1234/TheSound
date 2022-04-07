package com.example.thesound.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status : LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String> = _msg

    init {
        _msg.value = ""
        _status.value = false

    }
    fun signUp(email: String, password: String) {
        val task = Firebase.auth.createUserWithEmailAndPassword(email, password)
        task.addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                //findNavController().navigate(R.id.signInFragment)
                _status.value = true
            } else {
                _msg.value = it.exception?.message
                //showSnackbar(view, it.exception?.message.toString())
            }
        }
    }

}