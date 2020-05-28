package com.example.todonotesapp.recyclerView_mvvm_livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MvvmViewModel :ViewModel() {

    var userLiveData: MutableLiveData<ArrayList<MvvmModel>>? = null
    var userArrayList: ArrayList<MvvmModel>? = null

    fun MainViewModel() {
        userLiveData = MutableLiveData<ArrayList<MvvmModel>>()

        // call your Rest API in init method
        init()
    }

    private fun init() {
        val user = MvvmModel("Darknight","Best rating movie" )
        userArrayList = ArrayList()
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
    }

    fun getUserMutableLiveData(): MutableLiveData<ArrayList<MvvmModel>>? {
        return userLiveData
    }
}