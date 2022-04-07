package com.example.thesound.eventos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesound.R
import com.example.thesound.adapter.EventosAdapter
import com.example.thesound.database.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EventosFragment : Fragment() {

    companion object {
        fun newInstance() = EventosFragment()
    }

    private lateinit var viewModel: EventosViewModel
    private lateinit var listEventos : RecyclerView
    private lateinit var fabAddEvento : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.eventos_fragment, container, false)

        fabAddEvento = view.findViewById(R.id.fabAddEvento)

        listEventos = view.findViewById(R.id.listEventos)
        listEventos.layoutManager = LinearLayoutManager(requireContext())

        val appDatabase = AppDatabase.getInstance(requireActivity().applicationContext)
        viewModel = ViewModelProvider(this, EventosViewModelFactory(appDatabase.eventosDao())).get(EventosViewModel::class.java)
        viewModel.eventos.observe(viewLifecycleOwner){
            if(!it.isNullOrEmpty())
                listEventos.adapter = EventosAdapter(it)
            //Snackbar.make(view, "${it.size}", Snackbar.LENGTH_LONG).show()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAddEvento.setOnClickListener {
            findNavController().navigate(R.id.generoAddFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser == null) {
            findNavController().navigate(R.id.signInFragment)
        }
    }

}