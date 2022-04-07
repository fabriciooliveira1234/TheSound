package com.example.thesound.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thesound.R
import com.example.thesound.database.eventos.Eventos
import com.example.thesound.database.eventos.EventoseGenero
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EventosAdapter(private val eventos:List<EventoseGenero>)
    : RecyclerView.Adapter<EventosAdapter.EventosAdapterViewHolder>() {
        class EventosAdapterViewHolder(itemView : View)
            : RecyclerView.ViewHolder(itemView){
            val genero = itemView.findViewById<TextView>(R.id.txtItemRecycleGenero)
            val cidade = itemView.findViewById<TextView>(R.id.txtItemRecycleCidade)
            val estado = itemView.findViewById<TextView>(R.id.txtItemRecycleEstado)
            val imgViewRecycle = itemView.findViewById<ImageView>(R.id.imgViewRecycle)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventosAdapterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycle_list_eventos, parent, false)
        return EventosAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventosAdapterViewHolder, position: Int) {
        val eventoEgenero = eventos[position]
        holder.genero.text = eventoEgenero.eventos.genero
        holder.cidade.text = eventoEgenero.eventos.cidade
        holder.estado.text = eventoEgenero.eventos.estado
        setImageView(holder.imgViewRecycle, eventoEgenero.eventos.id!!)
    }

    override fun getItemCount(): Int = eventos.size

    fun setImageView(imageView: ImageView, id: Long){
        Firebase
            .storage
            .reference
            .child("eventos/Foto_testes${id}.jpeg")
            .getBytes((1024*1024).toLong())
            .addOnSuccessListener{
                val bitmap = BitmapFactory
                    .decodeByteArray(it,0,it.size)
                imageView.setImageBitmap(bitmap)

            }
    }
}