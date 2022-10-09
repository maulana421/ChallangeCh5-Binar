package com.solanacode.challangech5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.solanacode.challangech5.databinding.FragmentDetailMovieBinding
import com.solanacode.challangech5.viewmodel.MovieViewModel


class DetailMovieFragment : Fragment() {

    private lateinit var binding : FragmentDetailMovieBinding
    private lateinit var movieViewModel: MovieViewModel
    private val i by navArgs<DetailMovieFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMovieBinding.inflate(layoutInflater)
        movieViewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieViewModel.callDetailApifilm(i.fillmId)
        movieViewModel.liveDataDetailFilms().observe(requireActivity()){
            if(it != null){
                binding.apply {
                    Glide.with(root.context).load(it.image).into(imageTVShow)
                    textName.text = it.title
                    tvjapanTitle.text = it.originalTitle
                    tvOriTitle.text = it.originalTitleRomanised
                    tvRealaseDate.text = it.releaseDate
                    textDescription.text = it.description
                    textRating.text = it.rtScore
                    textRuntime.text = it.runningTime
                    buttonWebsite.setOnClickListener { its ->
                        val x = Intent(Intent.ACTION_VIEW)
                        x.data= Uri.parse(it.url)
                        startActivity(x)
                    }


                }
        }

        }
    }




}