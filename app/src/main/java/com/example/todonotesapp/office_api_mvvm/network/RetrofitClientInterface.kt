package com.example.todonotesapp.office_api_mvvm.network

import com.example.todonotesapp.office_api_mvvm.model.login.request.LoginUserRequest
import com.example.todonotesapp.office_api_mvvm.model.login.response.UserLoginMainResponse
import com.example.todonotesapp.office_api_mvvm.model.socialEntry.request.response.SocialEntryResponseMain
import retrofit2.Call
import retrofit2.http.*

interface RetrofitClientInterface {

    @FormUrlEncoded
    @POST("SocialEntry")
    fun getUserResult(@Field("name") name:String,
                      @Field("email") email:String,
                      @Field("profile_pic") profile_pic:String,
                      @Field("device_id") device_id:String,
                      @Field("device_type") device_type: String
                    ) : Call<SocialEntryResponseMain>


    @Headers("Content-Type : application/json")
    @POST("login")
    fun getUserMobile(@Body phone_no: LoginUserRequest): Call<UserLoginMainResponse>

}