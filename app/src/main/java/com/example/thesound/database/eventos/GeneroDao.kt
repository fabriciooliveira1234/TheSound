package com.example.thesound.database.eventos

import androidx.room.*

@Dao
interface GeneroDao {


    @Insert
    suspend fun insert(genero: Genero) : Long

    @Query("SELECT * FROM genero WHERE id = :id")
    suspend fun read(id : Long) : Genero

    @Update
    suspend fun update(genero: Genero)

    @Delete
    suspend fun delete(genero: Genero)

    @Query("SELECT * FROM genero ")
    suspend fun list() : List<Genero>

    /*@Query("SELECT * FROM eventos ")
    suspend fun listWithAutores() : List<EventoseGenero>*/


}