package com.example.thesound.signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thesound.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var auth : FirebaseAuth
    private lateinit var editTxtEmailCadastro : EditText
    private lateinit var editTxtSenhaCadastro : EditText
    private lateinit var editTxtSenhaCadastroConfirm : EditText
    private lateinit var btnEntrarCadastro : AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sign_up_fragment, container, false)

        editTxtEmailCadastro = view.findViewById(R.id.editTxtEmailCadastro)
        editTxtSenhaCadastro = view.findViewById(R.id.editTxtSenhaCadastro)
        editTxtSenhaCadastroConfirm = view.findViewById(R.id.editTxtSenhaCadastroConfirm)
        btnEntrarCadastro = view.findViewById(R.id.btnEntrarCadastro)
        auth = Firebase.auth

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner){
            if(it){
                val email = editTxtEmailCadastro.text.toString()
                salvarPrefUserEmail(email)
                //escreverLogNoArquivo("Tentativa de Acesso: \tok\n")
                findNavController().navigate(R.id.signInFragment)
            }
        }

        viewModel.msg.observe(viewLifecycleOwner){
            if(it.isNotBlank()) {
                showSnackbar(view, it)
                //escreverLogNoArquivo("Tentativa de Acesso: Usuário Inválido\n")
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnEntrarCadastro.setOnClickListener {
            val email = editTxtEmailCadastro.text.toString()
            val password = editTxtSenhaCadastro.text.toString()
            val confirmPassword = editTxtSenhaCadastroConfirm.text.toString()


            if(password.compareTo(confirmPassword) == 0 && email.isNotBlank()){
                viewModel.signUp(email,password)
            }else{
                showSnackbar(view, "Senhas não conferem!")
            }
        }
    }

    private fun salvarPrefUserEmail(email : String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        val editPref = sharedPref.edit()
        editPref.putString("user_email", email)
        Log.d("Email preferences", "set user_email")
        editPref.apply()

    }

    private fun showSnackbar(view: View, msg: String) {
        Snackbar
            .make(view, msg , Snackbar.LENGTH_LONG)
            .show()
    }

}