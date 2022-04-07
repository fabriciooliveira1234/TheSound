package com.example.thesound.database.chat

import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp.now
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat


class Chat(
    val msg : String = "",
    val user : String = "",
    val date : Timestamp = now(),
    //val ordem : Int = 0,
    @DocumentId val id : String? = null,
){
    override fun toString() : String {
        val sdf = SimpleDateFormat("dd/MM/yy HH:mm")
        val date = sdf.format(date.toDate())
        return "${user} ${date} \n : ${msg}"
    }
}

object ChatDao {
    private val collection = Firebase
        .firestore
        .collection("chat")

    fun insert(chat: Chat): Task<DocumentReference> {
        return collection.add(chat)
    }

    fun read(chat: Chat): Task<DocumentSnapshot> {
        return collection
            .document(chat.id!!)
            .get()
    }

    fun update(chat: Chat): Task<Void> {
        return collection
            .document(chat.id!!)
            .set(chat, SetOptions.merge())
    }

    fun delete(chat: Chat): Task<Void> {
        return collection.document(chat.id!!).delete()
    }

    fun list(): Query {
        return collection
            .orderBy("date")
            //.get()
    }

    //Limitar
    fun listLimit(): Task<QuerySnapshot> {
        return collection
            .limit(2)
            .get()
    }

    //Ordenar com filtro
    fun listFilterOrder(): Task<QuerySnapshot> {
        return collection
            .whereGreaterThan("user","fabrici@s")
            .orderBy("user")
            .orderBy("ordem")
            .get()
    }

    //Ordenar
    fun listOrder(): Task<QuerySnapshot> {
        return collection
            .orderBy("ordem", Query.Direction.ASCENDING)
            .get()
    }

    //Filtro
    fun listFiltered(): Task<QuerySnapshot> {
        return collection
            .whereEqualTo("user", "fabrici@s") //Filtro
            .get()
    }
}
