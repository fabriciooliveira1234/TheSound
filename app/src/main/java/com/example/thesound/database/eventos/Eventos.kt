package com.example.thesound.database.eventos

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

// Entidade que será entregue para a Room
@Entity
data class Eventos(
    @NonNull val genero : String,
    @NonNull val cidade : String,
    @NonNull val estado : String,
    @PrimaryKey val id : Long? = null
    //val generoId : Long
)

@Entity
data class Genero (
    //@NonNull val tipo : String,
    @NonNull val eventosId : Long,
    @PrimaryKey val id: Long? = null
)

//1xn
data class EventoseGenero(
    @Embedded val eventos: Eventos,
    @Relation(
        parentColumn = "id",
        entityColumn = "eventosId"
    ) val genero: MutableList<Genero>
)

//nxn
/*@Entity(primaryKeys = ["generoId", "eventoId])
data class EventoGeneroRef(
   val generoId : Long,
   val eventoId : Long

data class EventoseGenero(
    @Embedded val eventos : Eventos
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
        associateBy = Junction(EventoGeneroRef::class)
   ) val genero : List<Genero>

Pode tbm fazer de forma inversa GeneroeEvento
   */

//1x1
/*data class EventoseGenero(
    @Embedded val eventos: Eventos,
    @Relation(
        parentColumn = "id",
        entityColumn = "eventosId"
    ) val genero: Genero
)*/




