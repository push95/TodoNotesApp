package com.example.todonotesapp.office_api_mvvm.repository
import androidx.lifecycle.MutableLiveData
import com.example.todonotesapp.office_api_mvvm.model.socialEntry.request.response.SocialEntryResponseMain
import com.example.todonotesapp.office_api_mvvm.network.RetrofitClientApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialEntryRepository {

    fun userDetails(name :String, email: String, profile_pic :String,device_id :String, device_type :String) {

        val userDetailsResponse =MutableLiveData<SocialEntryResponseMain>()
        val call: Call<SocialEntryResponseMain> = RetrofitClientApi
            .getRetrofitClientInterface.getUserResult(name,email,profile_pic,device_id,device_type)
        call.enqueue(object : Callback<SocialEntryResponseMain> {
            override fun onResponse(call: Call<SocialEntryResponseMain>, response: Response<SocialEntryResponseMain>
            ) {
                if (response != null){
                    if (response.isSuccessful){
                        userDetailsResponse.value=response.body()
                    }
                }
            }
            override fun onFailure(call: Call<SocialEntryResponseMain>, t: Throwable) {

            }
        })
    }

}