package com.example.todonotesapp.office_api_mvvm.model.login.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserLoginMainResponse : Serializable {

    @SerializedName("status")
    public var status : Boolean?=null

    @SerializedName("message")
    public var  message: String?=null

    @SerializedName("result")
    public var  result: UserLoginResults?=null

}