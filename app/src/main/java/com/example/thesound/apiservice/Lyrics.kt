package com.example.thesound.apiservice

class Lyrics(val artista : String? = null,
             val musica : String? = null,
             val id : Long? = null) {

    override fun toString() = "$artista"
    }
