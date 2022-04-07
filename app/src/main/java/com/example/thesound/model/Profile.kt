package com.example.thesound.model

import com.google.firebase.firestore.DocumentId

data class Profile (
    val endereco : String,
    val idade : Int,
    val music : String,
    @DocumentId val id : String? = null
    )
