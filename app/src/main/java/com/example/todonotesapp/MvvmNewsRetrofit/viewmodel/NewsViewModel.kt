package com.example.todonotesapp.MvvmNewsRetrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todonotesapp.MvvmNewsRetrofit.model.NewsResponseWrapperModel
import com.example.todonotesapp.MvvmNewsRetrofit.repository.NewsRepos


class NewsViewModel :ViewModel() {
    private var API_KEY=""
    private var mutableLiveData: MutableLiveData<NewsResponseWrapperModel>? = null
    private var newsRepository: NewsRepos? = null

    fun init() {
        if (mutableLiveData != null) {
            return
        }
        newsRepository = NewsRepos.getInstance()
        mutableLiveData = newsRepository!!.getNews("us", API_KEY)
    }

    fun getNewsRepository(): LiveData<NewsResponseWrapperModel>? {
        return mutableLiveData
    }
}