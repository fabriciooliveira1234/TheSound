package com.example.thesound.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thesound.R
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var imgPerfilFoto : ImageView
    private lateinit var editTxtPerfilEndereco : EditText
    private lateinit var editTxtPerfilIdade : EditText
    private lateinit var editTxtPerfilMusica : EditText
    private lateinit var btnSalvarPefil : AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        imgPerfilFoto = view.findViewById(R.id.imgPerfilFoto)
        editTxtPerfilEndereco = view.findViewById(R.id.editTxtPerfilEndereco)
        editTxtPerfilIdade = view.findViewById(R.id.editTxtPerfilIdade)
        editTxtPerfilMusica = view.findViewById(R.id.editTxtPerfilMusica)
        btnSalvarPefil = view.findViewById(R.id.btnSalvarPerfil)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner){
            if(it)
                findNavController().navigate(R.id.dashBoardFragment)
        }

        viewModel.msg.observe(viewLifecycleOwner){
            if(it.isNotBlank())
                Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getContent = registerForActivityResult(
            ActivityResultContracts
                .TakePicturePreview()){
            imgPerfilFoto.setImageBitmap(it)
            viewModel.setFoto(it)
        }

        imgPerfilFoto.setOnClickListener {
            getContent.launch()
        }

        btnSalvarPefil.setOnClickListener {
            val endereco = editTxtPerfilEndereco.text.toString()
            val idade = editTxtPerfilIdade.text.toString()
            val music = editTxtPerfilMusica.text.toString()

            viewModel.savePerfil(endereco, idade, music)
        }
    }



}