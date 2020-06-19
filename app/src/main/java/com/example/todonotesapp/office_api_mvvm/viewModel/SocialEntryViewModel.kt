package com.example.todonotesapp.office_api_mvvm.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todonotesapp.office_api_mvvm.model.socialEntry.request.response.SocialEntryResponseMain
import com.example.todonotesapp.office_api_mvvm.repository.SocialEntryRepository

class SocialEntryViewModel() :ViewModel() {
    private var repository :SocialEntryRepository =SocialEntryRepository()
    private var socialEntryViewModel =MutableLiveData<SocialEntryResponseMain>()
    var name :String?=null
    var email :String?=null
    var deviceType :String?=null
    var deviceID :String?=null
    var profilePic :String?=null

    fun getViewModelData(name :String , email :String ,deviceType :String,profilePic :String ,deviceID :String): MutableLiveData<SocialEntryResponseMain>{
        this.name=name
        this.email=email
        this.deviceType=deviceType
        this.profilePic=profilePic
        this.deviceID=deviceID
        socialEntryViewModel= repository.userDetails(name,email,deviceType,deviceID,profilePic) as MutableLiveData<SocialEntryResponseMain>
        return socialEntryViewModel
    }


}