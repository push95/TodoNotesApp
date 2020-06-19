package com.example.todonotesapp.office_api_mvvm.model.login.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginUserRequest:Serializable{
    @SerializedName("phone_no")
    public var phone_no :String?=null
}