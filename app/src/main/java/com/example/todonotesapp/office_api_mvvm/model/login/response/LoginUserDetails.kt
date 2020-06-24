package com.example.todonotesapp.office_api_mvvm.model.login.response
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginUserDetails :Serializable {

    @SerializedName("id")
    public var id :Int?=null

    @SerializedName("name")
    public var name :String?=null

    @SerializedName("email")
    public var email :String?=null


    @SerializedName("phone_no")
    public var phone_no :String?=null



    @SerializedName("dob")
    public var dob :String?=null


    @SerializedName("gender")
    public var gender :String?=null

    @SerializedName("city")
    public var city :String?=null

    @SerializedName("is_existed")
    public var is_existed :Int?=null

    @SerializedName("is_deleted")
    public var is_deleted :Int?=null

    @SerializedName("is_active")
    public var is_active :Int?=null

    @SerializedName("user_type")
    public var user_type :String?=null

    @SerializedName("device_id")
    public var device_id :String?=null

    @SerializedName("device_type")
    public var device_type :String?=null

}