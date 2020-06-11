package com.example.todonotesapp.fragment.notesFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.todonotesapp.R
import com.example.todonotesapp.databinding.FragmentLoginBinding
import org.json.JSONObject
import kotlin.math.log


class LoginFrag : Fragment()  {
    var rootView :View?=null
    lateinit var loginBinding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       /* loginBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        rootView= loginBinding.root
        //loginBinding.login=this
        return rootView*/
        loginBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_login, container,false)
        var jsonString :String=getData()

        //get JSONObject  from json file
        var obj=JSONObject(jsonString)
        var userResults =obj.getJSONObject("result")
        loginBinding.tvIdDynamic.text=userResults.getJSONObject("user_detail").getString("id")
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
        return loginBinding.root
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

