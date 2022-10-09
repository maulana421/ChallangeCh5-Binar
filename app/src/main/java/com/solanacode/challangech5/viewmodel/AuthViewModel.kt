package com.solanacode.challangech5.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solanacode.challangech5.api.Api
import com.solanacode.challangech5.model.UserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel: ViewModel() {

    val dataLogin : MutableLiveData<ArrayList<UserResponseItem>> = MutableLiveData()
    val dataRegister : MutableLiveData<UserResponseItem> = MutableLiveData()
    val dataUser: MutableLiveData<UserResponseItem> = MutableLiveData()
    val putDataUser: MutableLiveData<UserResponseItem> = MutableLiveData()
    val postDataUser: MutableLiveData<UserResponseItem> = MutableLiveData()

    fun doLogin(){
        Api.instance.signIn().enqueue(object : Callback<ArrayList<UserResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<UserResponseItem>>,
                response: Response<ArrayList<UserResponseItem>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    dataLogin.postValue(body)
                }else{
                    Log.d("notSuccess","${response.body()}")
                    dataLogin.postValue(null)
                }
            }

            override fun onFailure(call: Call<ArrayList<UserResponseItem>>, t: Throwable) {
                dataLogin.postValue(null)
                Log.d("onFailure","${t.message}")
            }

        })
    }


    fun doRegister(name:String,username:String,password:String,id:String){
        Api.instance.signUp(UserResponseItem(username,name,password,""))
            .enqueue(object : Callback<UserResponseItem>{
                override fun onResponse(call: Call<UserResponseItem>, response: Response<UserResponseItem>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        dataRegister.postValue(body)
                    }else{
                        dataRegister.postValue(null)
                        Log.d("notSuccess","${response.body()}")
                    }
                }
                override fun onFailure(call: Call<UserResponseItem>, t: Throwable) {
                    dataRegister.postValue(null)
                    Log.d("OnFailure","${t.message}")
                }

            })
    }

    fun getUser(id :String){
        Api.instance.getDetailUser(id).enqueue(object : Callback<UserResponseItem>{
            override fun onResponse(call: Call<UserResponseItem>, response: Response<UserResponseItem>) {
                if(response.isSuccessful){
                    val personal = response.body()
                    dataUser.postValue(personal)
                }
            }

            override fun onFailure(call: Call<UserResponseItem>, t: Throwable) {
                dataUser.postValue(null)
                Log.d("OnFail","${t.message}")
            }

        })

    }

    fun loginObserver():MutableLiveData<ArrayList<UserResponseItem>> = dataLogin
    fun registerObserver():MutableLiveData<UserResponseItem> = dataRegister


    fun updateDataUser(id : String,name : String,username :String,password :String){
        Api.instance.updateUser(id,UserResponseItem(username,name,password,id))
            .enqueue(object : Callback<UserResponseItem>{
                override fun onResponse(
                    call: Call<UserResponseItem>,
                    response: Response<UserResponseItem>
                ) {
                    if(response.isSuccessful){
                        val personal = response.body()
                        putDataUser.postValue(personal)
                    }
                }

                override fun onFailure(call: Call<UserResponseItem>, t: Throwable) {
                    dataUser.postValue(null)
                    Log.d("OnFail","${t.message}")
                }

            })
    }
}