package com.solanacode.challangech5

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.solanacode.challangech5.databinding.FragmentRegisterBinding
import com.solanacode.challangech5.viewmodel.AuthViewModel


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sharedPref : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        sharedPref = context?.getSharedPreferences("getdataUser", Context.MODE_PRIVATE)!!
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRegister()

    }

    private fun userRegister(){
        binding.btnRegister.setOnClickListener {
            val name = binding.etnamaReg.text.toString().trim()
            val username = binding.etusernmaeReg.text.toString().trim()
            val password = binding.etpasswordReg.text.toString().trim()

            if(name.isNotBlank() && username.isNotBlank() && password.isNotBlank() ){
                authViewModel.doRegister(name, username, password,"")
                authViewModel.registerObserver().observe(requireActivity()){
                    if(it != null){
                        Toast.makeText(requireActivity(), "Register Succesfully", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
                    }else{
                        Toast.makeText(requireActivity(), "Null", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(requireActivity(), "Field Cannot be empety maszzeh", Toast.LENGTH_SHORT).show()
            }
        }
    }



}