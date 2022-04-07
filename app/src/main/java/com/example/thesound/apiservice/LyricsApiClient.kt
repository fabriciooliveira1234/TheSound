package com.example.thesound.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LyricsApiClient {
    private val retrofit = Retrofit
        .Builder()
        .baseUrl("http://10.0.2.2:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun endpoint() = retrofit.create(Endpoint::class.java)
}