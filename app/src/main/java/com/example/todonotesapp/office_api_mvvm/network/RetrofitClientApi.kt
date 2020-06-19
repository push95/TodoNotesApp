package com.example.todonotesapp.office_api_mvvm.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientApi {


    private const val BASE_URL: String="http://news.apponward.com/api/"
    val getRetrofitClientInterface: RetrofitClientInterface
        get() {
            val gson =GsonBuilder()
                .setLenient()
                .create()
            val interceptor= HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient =OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofitInstance =Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                 .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofitInstance.create(RetrofitClientInterface::class.java)



        }
}