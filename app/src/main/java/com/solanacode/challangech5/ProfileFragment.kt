package com.solanacode.challangech5

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.solanacode.challangech5.databinding.FragmentProfileBinding
import com.solanacode.challangech5.viewmodel.AuthViewModel
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var authViewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        sharedPref = context?.getSharedPreferences("getdataUser", Context.MODE_PRIVATE)!!
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sp = sharedPref.getString("id","not")
        authViewModel.getUser(sp.toString())
        authViewModel.dataUser.observe(requireActivity()){
            if(it != null){
                binding.apply {
                    tvusernameProfile.text = it.username
                    tvnameProfile.text = it.name
                }
            }
        }
        binding.tvLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
        }

        val showUsername = sharedPref.getString("username","not")
        binding.tvusernameProfile.text = showUsername
        updateUser()
        binding.btnchangeLanguage.setOnClickListener {
            setLocale("id")
        }

    }

    private fun updateUser(){
        binding.btnpdateProfile.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.updateProfileFragment)
        }
    }

    fun setLocale(lang: String?) {
            val myLocale = Locale(lang)
            val res = resources
            val conf = res.configuration
            conf.locale = myLocale
            res.updateConfiguration(conf, res.displayMetrics)
            Navigation.findNavController(binding.root).navigate(R.id.profileFragment)

    }




}