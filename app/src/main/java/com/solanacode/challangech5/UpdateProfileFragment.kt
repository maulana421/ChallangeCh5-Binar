package com.solanacode.challangech5

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.solanacode.challangech5.databinding.FragmentUpdateProfileBinding
import com.solanacode.challangech5.model.UserResponseItem
import com.solanacode.challangech5.viewmodel.AuthViewModel


class UpdateProfileFragment : Fragment() {

    private lateinit var binding: FragmentUpdateProfileBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProfileBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        sharedPref = context?.getSharedPreferences("getdataUser", Context.MODE_PRIVATE)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = sharedPref.getString("id","not")

        authViewModel.getUser(id.toString())
        authViewModel.dataUser.observe(requireActivity()){
            binding.apply {
                etnamaUpdate.setText(it.name)
                etusernmaeUpdate.setText(it.username)
                etpasswordUpdate.setText(it.password)
            }
        }
        binding.btnupdateProfile.setOnClickListener {
            val username = binding.etusernmaeUpdate.text.toString()
            val name = binding.etnamaUpdate.text.toString()
            val password = binding.etpasswordUpdate.text.toString()
            authViewModel.updateDataUser(id.toString(),username,name,password)
            authViewModel.putDataUser.observe(requireActivity()){
            if(it != null){
                Toast.makeText(requireActivity(), "Update Success", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(binding.root).navigate(R.id.profileFragment)
            }
        }
        }
    }


}