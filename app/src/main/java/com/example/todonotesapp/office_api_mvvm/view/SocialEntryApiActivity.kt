package com.example.todonotesapp.office_api_mvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.todonotesapp.R
import com.example.todonotesapp.databinding.ActivitySocialEntryApiBinding
import com.example.todonotesapp.office_api_mvvm.viewModel.SocialEntryViewModel


class SocialEntryApiActivity : AppCompatActivity() {
    private lateinit var  binding : ActivitySocialEntryApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_social_entry_api)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_social_entry_api)
        val viewModel =ViewModelProviders.of(this ).get(SocialEntryViewModel::class.java)
       // binding.viewModel=viewModel



       /* binding.btnSave.setOnClickListener{
            if (!validation()){
                *//*val call:Call<SocialEntryResponseMain> =RetrofitClientApi
                    .getRetrofitClientInterface.getUserResult(
                        binding.etName.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etProfile.text.toString(),
                        binding.etDeviceId.text.toString(),
                        binding.etDeviceType.text.toString())
                call.enqueue(object :Callback<SocialEntryResponseMain>{
                    override fun onResponse(call: Call<SocialEntryResponseMain>, response: Response<SocialEntryResponseMain>
                    ) {
                        if (response != null){
                            if (response.isSuccessful){
                                Toast.makeText(this@SocialEntryApiActivity, "Success", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<SocialEntryResponseMain>, t: Throwable) {
                        Toast.makeText(this@SocialEntryApiActivity, "Failed", Toast.LENGTH_SHORT).show()

                    }
                })*//*
            }
        }*/
    }


    private fun validation(): Boolean {
        when {
            binding.etName.text.toString().isEmpty() -> {
                binding.etName.error="Please enter name "
                binding.etName.requestFocus()
                return false
            }
            binding.etEmail.text.toString().isEmpty() -> {
                binding.etEmail.error="Please enter Email "
                binding.etEmail.requestFocus()
                return false
            }
            binding.etProfile.text.toString().isEmpty() -> {
                binding.etProfile.error="Please enter profile "
                binding.etProfile.requestFocus()
                return false
            }
            binding.etDeviceId.text.toString().isEmpty() -> {
                binding.etDeviceId.error="Please enter DeviceId "
                binding.etDeviceId.requestFocus()
                return false
            }
            binding.etDeviceType.text.toString().isEmpty() -> {
                binding.etDeviceType.error="Please enter Device Type "
                binding.etDeviceType.requestFocus()
                return false
            }
            else -> return true
        }
    }



}


