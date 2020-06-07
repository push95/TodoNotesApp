package com.example.todonotesapp.activity.anotherExample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todonotesapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {
    /*lateinit var binding: ActivityMain2Binding*/

    lateinit var mAuth:FirebaseAuth
    var mCurrentUser: FirebaseUser?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.layout_social_login)
        mAuth =FirebaseAuth.getInstance()
        mCurrentUser=mAuth.currentUser
        /*sign_out_button.setOnClickListener{
            val bottomDialog =BottomSheetDialog(this, R.style.BottomSheetDialog)
            val view =layoutInflater.inflate(R.layout.dialog_otp, null)
            bottomDialog.setContentView(view)
            bottomDialog.show()

        }*/

        }

    override fun onStart() {
        super.onStart()
        if (mCurrentUser ==null){
            val intent= Intent(this, NavigationDrawerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }

}


