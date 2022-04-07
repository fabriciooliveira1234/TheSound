package com.example.thesound.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thesound.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DashBoardFragment : Fragment() {

    companion object {
        fun newInstance() = DashBoardFragment()
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: DashBoardViewModel
    private lateinit var dashboardRadio: ImageButton
    private lateinit var dashboardEventos: ImageButton
    private lateinit var dashBoardBatePapo : ImageButton
    private lateinit var dashboardSair : ImageButton
    private lateinit var dashBoardLyricsApi : ImageButton
    //private lateinit var imgTeste : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dash_board_fragment, container, false)
        auth = Firebase.auth
        viewModel = ViewModelProvider(this).get(DashBoardViewModel::class.java)
        dashboardRadio = view.findViewById(R.id.dashboardRadio)
        dashboardEventos = view.findViewById(R.id.dashboardEventos)
        dashBoardBatePapo = view.findViewById(R.id.dashboardBatePapo)
        dashboardSair = view.findViewById(R.id.dashboardSair)
        dashBoardLyricsApi = view.findViewById(R.id.dashboardLyrcsApi)
        return view
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser == null) {
            findNavController().navigate(R.id.signInFragment)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //addDadosFirestore()
        //downloadStorage(imgTeste)
        //uploadStorage()
        dashboardRadio.setOnClickListener {
            findNavController().navigate(R.id.radioFragment)
        }
        dashboardEventos.setOnClickListener {
            findNavController().navigate(R.id.eventosFragment)
        }
        dashBoardBatePapo.setOnClickListener {
            findNavController().navigate(R.id.chatFragment2)
        }
        dashboardSair.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.signInFragment)
        }
        dashBoardLyricsApi.setOnClickListener {
            findNavController().navigate(R.id.lyricsApiFragment)
        }
    }
}

    /*fun addDadosFirestore(){
        //Instancia da classe FireBseFirestore
        val db = Firebase.firestore

        //Acessar a coleção
        val collection = db.collection("eventos")

        val task = collection.get()
        task.addOnCompleteListener {
            if(it.isComplete){
                val result = it.result
                val docs = result!!.toObjects(EventosComentarios::class.java)
                docs.forEach{doc ->
                    Log.d("FirestoreExemplo","${doc.genero}")
                }
            }else
                Log.d("FirestoreExemplo","${it.exception?.message}")
        }
    }*/

    /*fun addDadosFirestore() {
   //Instancia da classe FireBseFirestore
   val db = Firebase.firestore

   //Acessar a coleção
   val collection = db.collection("eventos")

   /*adcionar doc
   val task = collection.add(hashMapOf(
       "evento" to "Rock in Rio",
       "cidade" to "Rio de Janeiro",
       "estado" to "RJ"
   ))*/
   val task = collection.add(Eventos("Rock", "Brasilia", "DF"))
   task.addOnCompleteListener {
       if (it.isComplete)
           Log.d("FireStoreExemplo", "Evento inserido com sucesso")
       else
           Log.d("FirestoreExemplo", "${it.exception?.message}")
   }
}*/

    /*fun downloadStorage(imageView: ImageView){
        val storage = Firebase.storage
        val imagem = storage.reference.child("imagens/download.jpg")
        imagem
            .getBytes(1024 * 1024)
            .addOnSuccessListener {
                var bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                imageView.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Log.d("Google cloud Storage", "${it.message}")
            }
    }*/

    /*fun uploadStorage(){
        val storage = Firebase.storage
        val imagem = storage.reference.child("log/logsys.log")
        val fileUri = File(requireContext().filesDir, "logsys.log").toUri()
        val task = imagem
            .putFile(fileUri)
        task.addOnCompleteListener{
            if(it.isComplete) Log.d("Google Cloud Storage", "Upload realizado com sucesso.")
            else Log.d("Google Cloud Storage", "${it.exception!!.message}")

        }


    }*/

