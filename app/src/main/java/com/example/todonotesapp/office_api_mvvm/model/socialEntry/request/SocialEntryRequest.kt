package com.example.todonotesapp.office_api_mvvm.model.socialEntry.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SocialEntryRequest :Serializable {

    @SerializedName("name")
    private var name :String?=null

    @SerializedName("email")
    private var email :String?=null

    @SerializedName("profile_pic")
    private var profile_pic :String?=null

    @SerializedName("device_id")
    private var device_id :String?=null

    @SerializedName("device_type")
    private var device_type :String?=null



}