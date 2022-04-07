package com.example.thesound.signIn

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.ButtonBarLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thesound.R
import com.example.thesound.eventos.EventosViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.File

class signInFragment : Fragment() {

    companion object {
        fun newInstance() = signInFragment()
    }

    private lateinit var viewModel: SignInViewModel
    private lateinit var editTxtEmail : EditText
    private lateinit var editTxtSenha : EditText
    private lateinit var btnEntrar: AppCompatButton
    private lateinit var checkBoxLembrar : CheckBox
    //private lateinit var auth : FirebaseAuth
    private lateinit var txtSigninCadastrar : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sign_in_fragment, container, false)

        editTxtEmail = view.findViewById(R.id.editTxtEmail)
        editTxtSenha = view.findViewById(R.id.editTxtSenha)
        btnEntrar = view.findViewById(R.id.btnEntrar)
        checkBoxLembrar = view.findViewById(R.id.checkBoxLembrar)
        txtSigninCadastrar = view.findViewById(R.id.txtSigninCadastrar)
        //auth = Firebase.auth
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner){
            if(it) {
                escreverLogNoArquivo("Tentativa de Acesso: \tok\n")
                if (viewModel.hasFoto && viewModel.hasProfile){
                    findNavController().navigate(R.id.dashBoardFragment)
                }
                else
                    findNavController().navigate(R.id.profileFragment)
            }
        }

        viewModel.msg.observe(viewLifecycleOwner){
            if(it.isNotBlank()) {
                showSnackbar(view, it)
                escreverLogNoArquivo("Tentativa de Acesso: Usuário Inválido\n")
            }
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        verificarPrefUserEmail()
        verificarArquivoLog()

        btnEntrar.setOnClickListener {
            val email = editTxtEmail.text.toString()
            val password = editTxtSenha.text.toString()
            escreverLogNoArquivo("Tentativa de Acesso: ${email}\n")

            viewModel.signIn(email, password)

            escreverLogNoArquivo("Tentativa de Acesso:\t ok\n")
                if(checkBoxLembrar.isChecked) {
                    salvarPrefUserEmail(email)
                }

        }

        txtSigninCadastrar.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

    }

    override fun onResume() {
        verificarPrefUserEmail()
        super.onResume()
    }

    private fun verificarArquivoLog() {
        //val context = activity?.applicationContext
        val filesdir = requireContext().filesDir
        val arqLog = File(filesdir, "logsys.log")
        if(!arqLog.exists()) arqLog.createNewFile()
        if(arqLog.canWrite())
            Log.d("Log file","Arquivo de Log: ok!")
        else
            Log.d("Log file","Arquivo de log: Permissão Negada!")
        /*Acessar o diretório files.
        val file = File(context?.filesDir, "teste.txt")
        if (!file.exists()) file.createNewFile()*/
    }

    private fun escreverLogNoArquivo(msgLog : String){
        //FileOutputStream
        //openFileOutput -> files (filesDir)

        val msgLogDateTime = "" + msgLog
        requireContext()
            .openFileOutput("logsys.log", Context.MODE_APPEND)
            .use {
                it.write(msgLogDateTime.toByteArray())
                it.close()
            }

    }

    private fun salvarPrefUserEmail(email : String) {
        //Se for feito a chekagem deverá ser armazenado na preferência
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        val editPref = sharedPref.edit()
        editPref.putString("user_email", email)
        Log.d("Email preferences", "set user_email")
        editPref.apply()

    }

    private fun verificarPrefUserEmail() {
        //lê o armazenamento de preferencias para valorar o campo de email
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val userEmail = sharedPref!!.getString("user_email", null)
        Log.d("Email preferences", userEmail ?: "null")
        if(!userEmail.isNullOrBlank()) editTxtEmail.setText(userEmail)
    }

    private fun showSnackbar(view: View, msg: String) {
       Snackbar
           .make(view, msg , Snackbar.LENGTH_LONG)
           .show()
   }
}