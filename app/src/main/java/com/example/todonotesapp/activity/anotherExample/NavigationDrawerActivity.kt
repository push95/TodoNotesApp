package com.example.todonotesapp.activity.anotherExample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.todonotesapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class NavigationDrawerActivity : AppCompatActivity(){

    var et_mobile:EditText?=null
    var counntryCode:EditText?=null
    var feedback :TextView?=null
    var btn: Button?=null
    lateinit var mAuth: FirebaseAuth
    var mCurrentUser: FirebaseUser?=null
    lateinit var verificationCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        et_mobile = findViewById(R.id.mobile)
        counntryCode = findViewById(R.id.countrycode)
        btn = findViewById(R.id.sendotp)
        feedback = findViewById(R.id.feedback)
        mAuth = FirebaseAuth.getInstance()
        mCurrentUser = mAuth.currentUser

        /*var mCountryCode: String = counntryCode!!.text.toString()
        var mPhoneNumber: String = et_mobile!!.text.toString()
        var mCompleteNumber= "+$mCountryCode$mPhoneNumber"*/

        /*et_mobile!!.addTextChangedListener(object  :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(mCompleteNumber.length < 11){
                    Toast.makeText(this@NavigationDrawerActivity,"not field", Toast.LENGTH_SHORT).show()
                }else{
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        mCompleteNumber,
                        60,
                        TimeUnit.SECONDS,
                        this@NavigationDrawerActivity,
                        verificationCallbacks)
                    val bottomDialog = BottomSheetDialog(this@NavigationDrawerActivity, R.style.BottomSheetDialog)
                    val view =layoutInflater.inflate(R.layout.dialog_otp, null)
                    bottomDialog.setContentView(view)
                    bottomDialog.show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
                if(mCompleteNumber.length < 11){
                    Toast.makeText(this@NavigationDrawerActivity,"not field", Toast.LENGTH_SHORT).show()
                }else{
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        mCompleteNumber,
                        60,
                        TimeUnit.SECONDS,
                        this@NavigationDrawerActivity,
                        verificationCallbacks)//
                    val bottomDialog = BottomSheetDialog(this@NavigationDrawerActivity, R.style.BottomSheetDialog)
                    val view =layoutInflater.inflate(R.layout.dialog_otp, null)
                    bottomDialog.setContentView(view)
                    bottomDialog.show()
                }
            }
        })*/


        btn!!.setOnClickListener {
            var country_code: String = counntryCode!!.text.toString()
            var phone_number: String = et_mobile!!.text.toString()
            var complete_number= "+$country_code$phone_number"
            if (country_code.isEmpty() || phone_number.isEmpty()) {
                feedback!!.text = "pleased fill in the form"
                feedback!!.visibility=View.VISIBLE
            }
            else {
                btn!!.isEnabled = false
                feedback!!.visibility=View.GONE
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    complete_number,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    verificationCallbacks

                )
            }

        }

        verificationCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Toast.makeText(this@NavigationDrawerActivity, "Verification Completed", Toast.LENGTH_SHORT).show()
                signInWithPhone(phoneAuthCredential);
            }

            override fun onVerificationFailed(exception: FirebaseException){
                Toast.makeText(this@NavigationDrawerActivity, "Verification Failed", Toast.LENGTH_SHORT).show()
                if(exception is FirebaseAuthInvalidCredentialsException){
                    // INVALID REQUEST
                    Log.d("PHONEAUTh" ,"Invalid Credential" +exception.localizedMessage)
                }else if(exception is FirebaseTooManyRequestsException){
                    // SMS quote exceeded
                    Log.e("PHONEAUTh" , "SMS Quote Exceeded")
                }
            }

            override fun onCodeSent(s: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, token)
                Toast.makeText(this@NavigationDrawerActivity, "Code Sent", Toast.LENGTH_SHORT).show()
                /*val bottomDialog = BottomSheetDialog(this@NavigationDrawerActivity, R.style.BottomSheetDialog)
                val view =layoutInflater.inflate(R.layout.dialog_otp, null)
                bottomDialog.setContentView(view)
                bottomDialog.show()*/
                val intent= Intent(this@NavigationDrawerActivity, OTPActivity::class.java)
                intent.putExtra("data" ,"s")
                startActivity(intent)

            }
        }

    }

    private fun signInWithPhone(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult>{ task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Login SuccessFull", Toast.LENGTH_SHORT).show()
                    val intent=Intent(this, Main2Activity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Incorrect OTP", Toast.LENGTH_SHORT).show()

                }
            })

    }


    override fun onStart() {
        super.onStart()
        if (mCurrentUser !=null){
            val intent= Intent(this, Main2Activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }



}
