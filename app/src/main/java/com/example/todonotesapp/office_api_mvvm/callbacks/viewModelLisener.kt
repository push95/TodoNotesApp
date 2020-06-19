package com.example.todonotesapp.office_api_mvvm.callbacks

interface viewModelLisener {
    fun onInitialise()
    fun onSuccess()
    fun onFailure(error :String)

}