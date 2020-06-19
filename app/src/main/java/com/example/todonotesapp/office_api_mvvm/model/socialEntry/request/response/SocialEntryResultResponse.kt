package com.example.todonotesapp.office_api_mvvm.model.socialEntry.request.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SocialEntryResultResponse :Serializable {

    @SerializedName("user_detail")
    public var user_detail :SocialEntryUserDetailResponse?=null

    @SerializedName("token")
    public var token :String?=null


    @SerializedName("message")
    public var message :String?=null



    }