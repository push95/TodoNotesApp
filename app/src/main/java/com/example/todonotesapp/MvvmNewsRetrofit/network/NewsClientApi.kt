package com.example.todonotesapp.MvvmNewsRetrofit.network

import com.example.todonotesapp.MvvmNewsRetrofit.model.NewsResponseWrapperModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsClientApi {
    @GET("top-headlines")
    open fun getNewsList(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String?
    ): Call<NewsResponseWrapperModel?>?
}