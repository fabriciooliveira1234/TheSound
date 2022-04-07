package com.example.thesound.apiservice

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {
    //Listar
    @GET("/albuns")
    suspend fun list(
        @Query("_limit") limit: Int = 20,
        @Query("_sort") ordenacao: String = "artista"
    ): List<Lyrics>

    //filtar por atributo
    @GET("/albuns")
    suspend fun listComFiltro(
        @Query("artista_like") artists: String,
        @Query("_sort") ordenacao: String = "artista"
    ): List<Lyrics>

    //Ler
    @GET("/albuns/{id}")
    suspend fun exibir(
        @Path("id") id : Long
    ) : Lyrics

    /*Inserir
    @POST("albuns")
    suspend fun exibir() : Lyrics*/


}
