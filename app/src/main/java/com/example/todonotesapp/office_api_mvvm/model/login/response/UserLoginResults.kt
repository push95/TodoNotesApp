package com.example.todonotesapp.office_api_mvvm.model.login.response

import com.google.gson.annotations.SerializedName

class UserLoginResults {

    @SerializedName("token")
    public var token : String?=null

    @SerializedName("user_detail")
    public var user_detail : LoginUserDetails?=null

}