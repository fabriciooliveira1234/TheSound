package com.example.thesound.addEventos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.navigation.fragment.findNavController
import com.example.thesound.R
import com.example.thesound.database.AppDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EventoAddFragment : Fragment() {

    companion object {
        fun newInstance() = EventoAddFragment()
    }

    private lateinit var imgViewEventoFoto : ImageView
    private lateinit var editTxtEventoGenero : EditText
    private lateinit var editTxtEventoCidade : EditText
    private lateinit var editTxtEventoEstado : EditText
    private lateinit var btnEventoSalvar : Button

    private lateinit var viewModel: EventoAddViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.evento_add_fragment, container, false)

        imgViewEventoFoto = view.findViewById(R.id.imgViewEventoPhoto)
        editTxtEventoGenero = view.findViewById(R.id.editTxtEventoGenero)
        editTxtEventoCidade = view.findViewById(R.id.editTxtEventoCidade)
        editTxtEventoEstado = view.findViewById(R.id.editTxtEventoEstado)
        btnEventoSalvar = view.findViewById(R.id.btnEventoSalvar)

        val appDataBase = AppDatabase.getInstance(requireContext().applicationContext)
        val eventoDao = appDataBase.eventosDao()
        val eventoAddViewModelFactory = EventoAddViewModelFactory(eventoDao)
        viewModel = ViewModelProvider(
            this, eventoAddViewModelFactory)
            .get(EventoAddViewModel::class.java)

        viewModel.msg.observe(viewLifecycleOwner){
            if(!it.isNullOrBlank())
                Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
        }

        viewModel.status.observe(viewLifecycleOwner){
            if(it)
                findNavController().popBackStack()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getContent = registerForActivityResult(ActivityResultContracts
            .TakePicturePreview()){
            imgViewEventoFoto.setImageBitmap(it)
            viewModel.convertFoto(it)
        }
        imgViewEventoFoto.setOnClickListener {
            getContent.launch()
        }

        btnEventoSalvar.setOnClickListener {
            val genero = editTxtEventoGenero.text.toString()
            val cidade = editTxtEventoCidade.text.toString()
            val estado = editTxtEventoEstado.text.toString()
            viewModel.salvarEvento(genero,cidade,estado)
        }
    }

    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser == null) {
            findNavController().navigate(R.id.signInFragment)
        }
    }

}