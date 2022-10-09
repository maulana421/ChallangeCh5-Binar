package com.solanacode.challangech5.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solanacode.challangech5.api.Api
import com.solanacode.challangech5.api.ApiService
import com.solanacode.challangech5.model.ItemResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private val liveDataFilms : MutableLiveData<List<ItemResponseItem>> = MutableLiveData()
    private val liveDataDetailFilms : MutableLiveData<ItemResponseItem> = MutableLiveData()

    fun getLiveDataFilms() : MutableLiveData<List<ItemResponseItem>> = liveDataFilms
    fun liveDataDetailFilms() : MutableLiveData<ItemResponseItem> = liveDataDetailFilms


    fun showFilmList(){
        Api.filmInstance.getAllfilm()
            .enqueue(object : Callback<List<ItemResponseItem>>{
                override fun onResponse(
                    call: Call<List<ItemResponseItem>>,
                    response: Response<List<ItemResponseItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataFilms.postValue(response.body())
                        Log.d("kntl","${response.body()}")
                    }else{
                        liveDataFilms.postValue(null)
                        Log.d("notSuccess","${response.body()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemResponseItem>>, t: Throwable) {
                    liveDataFilms.postValue(null)
                    Log.d("onFailure","${t.message}")

                }

            })
    }

    fun callDetailApifilm(id: String){
        Api.filmInstance.getDetailFilm(id)
            .enqueue(object : Callback<ItemResponseItem>{
                override fun onResponse(
                    call: Call<ItemResponseItem>,
                    response: Response<ItemResponseItem>
                ) {
                    if (response.isSuccessful){
                        liveDataDetailFilms.postValue(response.body())
                    }else{
                        liveDataDetailFilms.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ItemResponseItem>, t: Throwable) {
                    liveDataDetailFilms.postValue(null)
                }

            })
    }



}