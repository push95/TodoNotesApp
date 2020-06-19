package com.example.todonotesapp.MvvmNewsRetrofit.model

class NewsResponseWrapperModel(
                                var status :String ,
                               var  totalResults :String,
                               var articles:ArrayList<NewsArticleResponseModel>
                            )