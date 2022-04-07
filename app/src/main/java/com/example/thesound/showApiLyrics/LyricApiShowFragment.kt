package com.example.thesound.showApiLyrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thesound.R

class LyricApiShowFragment : Fragment() {

    companion object {
        fun newInstance() = LyricApiShowFragment()
    }

    private lateinit var viewModel: LyricApiShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.lyric_api_show_fragment, container, false)

        val lyricsId = arguments?.getLong("lyricsId")

        viewModel = ViewModelProvider(this).get(LyricApiShowViewModel::class.java)
        viewModel.lyric.observe(viewLifecycleOwner){
            if(it != null)
                view.findViewById<TextView>(R.id.txtMostrarMusica).text = it.musica.toString()
        }
        if (lyricsId != null) {
            viewModel.buscarLyrics(lyricsId)
        }
        return view
    }

}