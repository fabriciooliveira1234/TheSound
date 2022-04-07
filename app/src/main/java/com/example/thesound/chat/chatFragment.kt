package com.example.thesound.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thesound.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class chatFragment : Fragment() {

    companion object {
        fun newInstance() = chatFragment()
    }

    private lateinit var editTxtMensagemChat : EditText
    private lateinit var btnMensagemEnviar : Button
    private lateinit var listViewChat : ListView
    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        editTxtMensagemChat = view.findViewById(R.id.editTxtMensagemChat)
        btnMensagemEnviar = view.findViewById(R.id.btnMensagemEnviar)
        listViewChat = view.findViewById(R.id.listViewChat)

        viewModel.msg.observe(viewLifecycleOwner){
            if(!it.isNullOrBlank()) Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }

        viewModel.chat.observe(viewLifecycleOwner){
            listViewChat.adapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,it
            )
            listViewChat.setOnItemClickListener { adapterView, view, i, l ->

            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnMensagemEnviar.setOnClickListener {
            val mensagem = editTxtMensagemChat.text.toString()
            viewModel.insert(mensagem)

        }

        listViewChat.setOnItemClickListener { adapterView, view, i, l ->
            viewModel.deleteItem(i)
        }
    }

    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser == null) {
            findNavController().navigate(R.id.signInFragment)
        }
    }

}