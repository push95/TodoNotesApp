package com.example.todonotesapp.activity.anotherExample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.todonotesapp.R
import com.example.todonotesapp.fragment.notesFragment.MainFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*

class OTPActivity : AppCompatActivity() ,View.OnClickListener {

    var otp :EditText?=null
    var verifyBtn :Button?=null
    var otpFeedback :TextView?=null
    lateinit var mAuth: FirebaseAuth
    var mCurrentUser: FirebaseUser?=null
    var data:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)
        otp=findViewById(R.id.et_otpverify)
        verifyBtn=findViewById(R.id.btn_otpVerify)
        otpFeedback=findViewById(R.id.otpFeedback)

        mAuth = FirebaseAuth.getInstance()
        mCurrentUser = mAuth.currentUser
        data=intent.getStringExtra("data")

        verifyBtn!!.setOnClickListener{
            var otpData:String=otp!!.text.toString()
            if (otpData.isEmpty()){
                otpFeedback!!.visibility=View.VISIBLE
                otpFeedback!!.text="Please fill in the form and try again"
            }else{
                var credential =PhoneAuthProvider.getCredential(data.toString() ,otp.toString())
                signInWithPhoneOtp(credential)
            }
        }
    }

    private fun signInWithPhoneOtp(credential: PhoneAuthCredential){
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

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.et_otpverify -> {

            }
        }

    }

}
