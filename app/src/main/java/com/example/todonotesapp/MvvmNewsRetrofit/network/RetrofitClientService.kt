package com.example.todonotesapp.MvvmNewsRetrofit.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClientService {
    companion object{
       private const val BASE_URL="https://newsapi.org/v2/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun <T> buildService(serviceClass: Class<T>?): T {
        return retrofit.create(serviceClass)
    }

}