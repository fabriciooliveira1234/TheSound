package com.example.thesound.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thesound.R
import com.google.android.material.snackbar.Snackbar

class LyricsApiFragment : Fragment() {

    private lateinit var viewModel: LyricsApiViewModel
    private lateinit var listAlbumLyrics : ListView
    private lateinit var imgButtonLyrics : ImageButton
    private lateinit var editTxtBuscaLyrics : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.lyrics_api_fragment, container, false)

        listAlbumLyrics = view.findViewById(R.id.listAlbunsLyrics)
        imgButtonLyrics = view.findViewById(R.id.imgButtonLyrics)
        editTxtBuscaLyrics = view.findViewById(R.id.editTxtBuscaLyrics)
        viewModel = ViewModelProvider(this).get(LyricsApiViewModel::class.java)

        viewModel.lyrics.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                listAlbumLyrics.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    it)

                listAlbumLyrics.setOnItemClickListener { adapterView, view, i, l ->
                    val lyricsId = it.get(i).id
                    val bundle = bundleOf(
                        "lyricsId" to lyricsId
                    )
                    findNavController().navigate(R.id.lyricApiShowFragment, bundle)
                }
            }
        }

        viewModel.msg.observe(viewLifecycleOwner){
            if(it.isNotBlank())
                Snackbar.make(view,it,Snackbar.LENGTH_LONG).show()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgButtonLyrics.setOnClickListener {
            val artistaFiltro = editTxtBuscaLyrics.text.toString()
            viewModel.filtrarLista(artistaFiltro)
        }
    }

}