package com.example.todonotesapp.office_api_mvvm.model.socialEntry.request.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SocialEntryResponseMain:Serializable {

    @SerializedName("status")
    public var status:Boolean?=null

    @SerializedName("result")
    public var result:SocialEntryResultResponse?=null
}