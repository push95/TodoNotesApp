package com.example.todonotesapp.office_api_mvvm.model.socialEntry.request.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SocialEntryUserDetailResponse :Serializable {

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

    @SerializedName("user_pic")
    public var user_pic :String?=null

    @SerializedName("is_existed")
    public var is_existed :String?=null





}