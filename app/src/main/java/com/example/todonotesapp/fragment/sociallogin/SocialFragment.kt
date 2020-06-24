package com.example.todonotesapp.fragment.sociallogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.todonotesapp.R
import com.example.todonotesapp.office_api_mvvm.model.login.response.UserLoginMainResponse

/**
 * A simple [Fragment] subclass.
 */
class SocialFragment : Fragment() {
    var rootView :View?=null
    var isSuccess :Boolean?=true
    var mIDTV:TextView?=null
    var mNameTV:TextView?=null
    var mEmailTV:TextView?=null
    var mPhoneTV:TextView?=null
    var mDOBTV:TextView?=null
    var mGenderTV:TextView?=null
    var mCityTV:TextView?=null
    var mExistTV:TextView?=null
    var mDeleteTV:TextView?=null
    var mActiveTV:TextView?=null
    var mUserTypeTV:TextView?=null
    var mDeviceIdTV:TextView?=null
    var mDeviceTypeTV:TextView?=null
    var mTokenTV:TextView?=null
    var mMessageTV:TextView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_social, container, false)
       // loginBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_login, container,false)
        var data: UserLoginMainResponse? = arguments!!.getSerializable("response") as UserLoginMainResponse?
        getAllIDS(rootView)
        getResponse(data)

        // getResponse(data)

     //   return loginBinding.root
        return rootView

    }

    private fun getResponse(data: UserLoginMainResponse?) {
        if(data != null){
            mTokenTV!!.text= data.result!!.token
            mMessageTV!!.text= data.message
            if (data.result!!.user_detail!= null){
                mIDTV!!.text= data.result!!.user_detail!!.id.toString()
                mNameTV!!.text= data.result!!.user_detail!!.name.toString()
                mEmailTV!!.text= data.result!!.user_detail!!.email.toString()
                mPhoneTV!!.text= data.result!!.user_detail!!.phone_no.toString()
                mDOBTV!!.text= data.result!!.user_detail!!.dob.toString()
                mGenderTV!!.text= data.result!!.user_detail!!.gender.toString()
                mCityTV!!.text= data.result!!.user_detail!!.city.toString()
                mExistTV!!.text= data.result!!.user_detail!!.is_existed.toString()
                mDeleteTV!!.text= data.result!!.user_detail!!.is_deleted.toString()
                mActiveTV!!.text= data.result!!.user_detail!!.is_active.toString()
                mUserTypeTV!!.text= data.result!!.user_detail!!.user_type.toString()
                mDeviceTypeTV!!.text= data.result!!.user_detail!!.device_type.toString()
                mDeviceIdTV!!.text= data.result!!.user_detail!!.device_id.toString()
            }

        }

    }

    private fun getAllIDS(rootView: View?) {
        mIDTV=rootView!!.findViewById(R.id.tv_idDynamic)
        mNameTV=rootView!!.findViewById(R.id.tv_name_Dynamic)
        mEmailTV=rootView!!.findViewById(R.id.tv_enail_Dynamic)
        mPhoneTV=rootView!!.findViewById(R.id.tv_phone_Dynamic)
        mDOBTV=rootView!!.findViewById(R.id.tv_DoB_Dynamic)
        mGenderTV=rootView!!.findViewById(R.id.tv_gender_Dynamic)
        mCityTV=rootView!!.findViewById(R.id.tv_city_Dynamic)
        mExistTV=rootView!!.findViewById(R.id.tv_exit_Dynamic)
        mDeleteTV=rootView!!.findViewById(R.id.tv_delete_Dynamic)
       mActiveTV =rootView!!.findViewById(R.id.tv_active_Dynamic)
        mUserTypeTV=rootView!!.findViewById(R.id.tv_userType_Dynamic)
        mDeviceIdTV=rootView!!.findViewById(R.id.tv_deviceID_Dynamic)
        mDeviceTypeTV=rootView!!.findViewById(R.id.tv_deviceType_Dynamic)
        mTokenTV=rootView!!.findViewById(R.id.tv_token_Dynamic)
        mMessageTV=rootView!!.findViewById(R.id.tv_message_Dynamic)
    }



}
