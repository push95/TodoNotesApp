package com.example.todonotesapp.office_api_mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.todonotesapp.R
import com.example.todonotesapp.databinding.FragmentLoginBinding
import com.example.todonotesapp.office_api_mvvm.model.socialEntry.request.response.SocialEntryResultResponse


class LoginFrag : Fragment()  {
    var rootView :View?=null
    lateinit var loginBinding: FragmentLoginBinding
    var isSuccess :Boolean?=true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       /* loginBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        rootView= loginBinding.root
        //loginBinding.login=this
        return rootView*/
        /*val name = arguments!!.getString("name")
        val email = arguments!!.getString("email")
        val value = arguments!!.getString("profile_pic")*/
        /*loginBinding.tvIdDynamic.text=i
        loginBinding.tvNameDynamic.text=name
        loginBinding.tvEnailDynamic.text=email*/
        /* Picasso.get()
            .load(value)
            .resize(50, 50)
            .centerCrop()
            .into(loginBinding.ivProfile)*/
        // var jsonString :String=getData()

        //get JSONObject  from json file
        // var obj=JSONObject(jsonString)
        //  var userResults =obj.getJSONObject("result")
        /*  loginBinding.tvIdDynamic.text=userResults.getJSONObject("user_detail").getString("id")
          loginBinding.tvNameDynamic.text=userResults.getJSONObject("user_detail").getString("name")
          loginBinding.tvEnailDynamic.text=userResults.getJSONObject("user_detail").getString("email")
          loginBinding.tvPhoneDynamic.text=userResults.getJSONObject("user_detail").getString("phone_no")
          loginBinding.tvDoBDynamic.text=userResults.getJSONObject("user_detail").getString("dob")
          loginBinding.tvGenderDynamic.text=userResults.getJSONObject("user_detail").getString("gender")
          loginBinding.tvCityDynamic.text=userResults.getJSONObject("user_detail").getString("city")
          loginBinding.tvExitDynamic.text=userResults.getJSONObject("user_detail").getString("is_existed")
          loginBinding.tvDeleteDynamic.text=userResults.getJSONObject("user_detail").getString("is_deleted")
          loginBinding.tvActiveDynamic.text=userResults.getJSONObject("user_detail").getString("is_active")
          loginBinding.tvUserTypeDynamic.text=userResults.getJSONObject("user_detail").getString("user_type")
          loginBinding.tvDeviceIDDynamic.text=userResults.getJSONObject("user_detail").getString("device_id")
          loginBinding.tvDeviceTypeDynamic.text=userResults.getJSONObject("user_detail").getString("device_type")
          loginBinding.tvTokenDynamic.text=userResults.getString("token")
          return loginBinding.root*/



        loginBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_login, container,false)
        var data: SocialEntryResultResponse? = arguments!!.getSerializable("response") as SocialEntryResultResponse?
        getResponse(data)

        return loginBinding.root
    }

    private fun getResponse(data: SocialEntryResultResponse?){
        if(data != null){
            loginBinding.tvTokenDynamic.text= data!!.token
            loginBinding.tvMessageDynamic.text= data!!.message
                if (data!!.user_detail !=null){
                    loginBinding.tvIdDynamic.text= data.user_detail!!.id.toString()
                    loginBinding.tvNameDynamic.text= data.user_detail!!.name
                    loginBinding.tvEnailDynamic.text= data.user_detail!!.email
                    loginBinding.tvPhoneDynamic.text= data.user_detail!!.phone_no
                    loginBinding.tvDoBDynamic.text= data.user_detail!!.dob
                    loginBinding.tvGenderDynamic.text= data.user_detail!!.gender
                    loginBinding.tvCityDynamic.text= data.user_detail!!.user_pic
                    loginBinding.tvExitDynamic.text= data.user_detail!!.is_existed
                }
        }
    }

    private fun getData(): String {
            var jsonData :String ="{ \"result\" : " + "{\"user_detail\" :" + "{\"id\" :\"540\" , \"name\" :\"Dfdsf\" , \"email\" :\"custom\" , \"phone_no\" :\"7417396365\" , \"dob\" :\"2020-01-18\" , \"gender\" :\"female\" ,\"city\" :\"Ahmedabad\" , \"is_existed\" :\"0\" , \"is_deleted\" :\"0\" , \"is_active\" :\"1\" ,\"user_type\" :\"5\" , \"device_id\" :\"aaaaaaabbbbbb\" ,\"device_type\" :\"1\"}" + ",\"token\" :\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjU0MCwiaXNzIjoiaHR0cDovL25ld3pidWxiLmNvbS9hcGkvbG9naW4iLCJpYXQiOjE1OTE3Njc3MzksImV4cCI6MTU5MTg1NDEzOSwibmJmIjoxNTkxNzY3NzM5LCJqdGkiOiJqOXZ4bEx1dTJNMmdZd011In0.A2MilpFBWvqCYEv4R8FvSf7b8yc_TJUyrHnWXQm_xwk\"}" + ",\"message\" :\"successfully login\"}"
            return jsonData
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     /*   loginBinding.btnFb.setOnClickListener{
            val mainFragment =MainFragment()
            var transaction = activity!!.supportFragmentManager!!.beginTransaction()
            transaction!!.replace(R.id.container, mainFragment)
            transaction!!.addToBackStack(null)
            transaction!!.commit()
        }*/
    }







}

