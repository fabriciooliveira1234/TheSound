package com.example.thesound.database.eventos

import androidx.room.*

@Dao
interface EventosDao {

    //Inserir
    @Insert
    suspend fun insert(eventos: Eventos) : Long

    //Ler /ID
    @Query("SELECT * FROM eventos WHERE id = :id")
    suspend fun read(id : Long) : Eventos

    //Atualizar
    @Update
    suspend fun update(eventos: Eventos)

    //Excluir
    @Delete
    suspend fun delete(eventos: Eventos)

    //Listar
    @Query("SELECT * FROM eventos ")
    suspend fun list() : List<Eventos>

    @Transaction
    @Query("SELECT * FROM eventos ")
    suspend fun listWithAutores() : List<EventoseGenero>
}