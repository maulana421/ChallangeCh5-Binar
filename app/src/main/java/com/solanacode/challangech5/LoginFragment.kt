package com.solanacode.challangech5

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.solanacode.challangech5.databinding.FragmentLoginBinding
import com.solanacode.challangech5.model.UserResponseItem
import com.solanacode.challangech5.viewmodel.AuthViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        sharedPreferences = context?.getSharedPreferences("getdataUser", Context.MODE_PRIVATE)!!
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLogin()
        binding.tvRegister.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.registerFragment)
        }
    }

    private fun userLogin(){
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val datax : MutableList<UserResponseItem> = mutableListOf()

            authViewModel.doLogin()
            authViewModel.loginObserver().observe(requireActivity()){
                if(it != null){
                    it.forEach { its ->
                        datax.addAll(mutableListOf(its))
                        Log.d("YAAMPUNNN","$its")
                    }

                    val a = datax.filter { its ->
                        its.username == username && its.password.equals(password)
                    }

                    if(a.isNotEmpty()){
                        var id = a[0].id
                        var name = a[0].name

                        sharedPreferences.edit().apply {
                            putString("id",id)
                            putString("name",name)
                            apply()
                        }

                        Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
                        Toast.makeText(requireActivity(), "Login Success Hallo $name", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(requireActivity(), "Username $username or password $password is wrong baby", Toast.LENGTH_SHORT).show()
                    }


                }else{
                    Toast.makeText(requireActivity(), "Null", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }




}