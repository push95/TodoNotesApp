package com.example.todonotesapp.MvvmNewsRetrofit.repository

import androidx.lifecycle.MutableLiveData
import com.example.todonotesapp.MvvmNewsRetrofit.model.NewsResponseWrapperModel
import com.example.todonotesapp.MvvmNewsRetrofit.network.NewsClientApi
import com.example.todonotesapp.MvvmNewsRetrofit.network.RetrofitClientService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NewsRepos {
    private var newsRepository: NewsRepos? = null
    fun getInstance(): NewsRepos? {
       /* if (newsRepository == null) {
            newsRepository = NewsRepos()
        }*/
        return newsRepository
    }

    private var newsClientApi: NewsClientApi? = null

   /* private fun newsRepository() {
        newsClientApi = RetrofitClientService.buildService(NewsClientApi::class.java)
    }*/

    fun getNews(country: String?, key: String?): MutableLiveData<NewsResponseWrapperModel>? {
        val newsData: MutableLiveData<NewsResponseWrapperModel>? = MutableLiveData<NewsResponseWrapperModel>()
        newsClientApi!!.getNewsList(country, key)!!.enqueue(object : Callback<NewsResponseWrapperModel?>{
            override fun onResponse(
                call: Call<NewsResponseWrapperModel?>,
                response: Response<NewsResponseWrapperModel?>
            ) {
                if (response !=null){
                    if (response.isSuccessful){
                        newsData!!.value=response.body()
                    }
                }
            }
            override fun onFailure(call: Call<NewsResponseWrapperModel?>, t: Throwable) {
                newsData!!.value=null
            }
        })
        return newsData;
    }

}