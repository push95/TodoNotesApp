package com.example.todonotesapp.fragment.sociallogin


import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.todonotesapp.R
import com.example.todonotesapp.databinding.FragmentSocialLoginBinding
import com.example.todonotesapp.fragment.notesFragment.MainFragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.hbb20.CountryCodePicker
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer

class SocialLoginFragment : Fragment() {
    /** GOOGLE SIGNIN **/
    private val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var mFirebaseGoogleAuth: FirebaseAuth


    /**  Mobile  verify **/
    lateinit var verificationCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mResendToken: ForceResendingToken
    lateinit var phoneVerificationID: String
    lateinit var mFirebaseMobileAuth: FirebaseAuth
    var completeNumber: String? = null
    var mOTP: String? = null
    var isOTPData: String? = null
    var ccp: CountryCodePicker? = null
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken


    /**  Binding **/
    lateinit var binding: FragmentSocialLoginBinding


    // fb
    lateinit var callbackManager: CallbackManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_social_login, container, false)
        callbackManager = CallbackManager.Factory.create()
        mFirebaseMobileAuth = FirebaseAuth.getInstance()
        mFirebaseGoogleAuth = FirebaseAuth.getInstance()
        binding.ccp!!.registerCarrierNumberEditText(binding.etMobile)

        // for google
        configureGoogleSignIn()
        setupUI()


        /** BINDING **/

        binding.skipLogin.setOnClickListener {
            callNewFragment()
        }

        binding.llFbook!!.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("public_profile", "email"))
        }
        // callback Registration
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(result: LoginResult?) {
                    Toast.makeText(context, "Login Success", Toast.LENGTH_LONG).show()
                    callNewFragment()
                }

                override fun onCancel() {
                    Toast.makeText(context, "Login Cancelled", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException?) {
                    Toast.makeText(context, exception!!.message, Toast.LENGTH_LONG).show()

                }
            })

        /* binding.etMobile.setOnEditorActionListener(){ v, actionId, event ->
             if (actionId ==EditorInfo.IME_ACTION_DONE) {

                *//* Custom EditText with otp generated *//*
             *//* var countryCode: String = binding.etCountry!!.text.toString()
               var phoneNumber: String = binding.etMobile!!.text.toString()
                 completeNumber = "+$countryCode$phoneNumber"
                if (countryCode.isNotEmpty() || phoneNumber.isNotEmpty()) {
                    sendVerificationCode(completeNumber.toString())
                }*//*
                    completeNumber=binding.ccp!!.fullNumberWithPlus
                if (completeNumber.toString().isNotEmpty()){
                    sendVerificationCode(completeNumber.toString())
                }
                true
            }
                else{
                    false
                }




        }*/

        binding.etMobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                completeNumber = binding.ccp!!.fullNumberWithPlus
                if (completeNumber.toString()
                        .isNotEmpty() && completeNumber.toString().length < 12
                ) {
                    binding.etMobile.error = "failed"
                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                completeNumber = binding.ccp!!.fullNumberWithPlus
                if (completeNumber.toString()
                        .isNotEmpty() && completeNumber.toString().length > 12
                ) {
                    sendVerificationCode(completeNumber.toString())
                }
            }

        })


        verificationCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Toast.makeText(context, "Verification Completed", Toast.LENGTH_SHORT).show()
                signInWithPhone(phoneAuthCredential);
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                Toast.makeText(context, "Verification Failed", Toast.LENGTH_SHORT).show()
                if (exception is FirebaseAuthInvalidCredentialsException) {
                    // INVALID REQUEST
                    Log.d("PHONEAUTh", "Invalid Credential" + exception.localizedMessage)
                } else if (exception is FirebaseTooManyRequestsException) {
                    // SMS quote exceeded
                    Log.e("PHONEAUTh", "SMS Quote Exceeded")
                }
            }

            override fun onCodeSent(otpCode: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(otpCode, token)
                mOTP = otpCode
                Toast.makeText(context, "Code Sent", Toast.LENGTH_SHORT).show()
                /*  var data =Bundle()
                  data.putString("hello" ,  "world")
                  socialLoginFragment!!.arguments=data
                  var values=socialLoginFragment
                  openBottomSheet(values)*/
                openBottomSheet()
            }
        }
        return binding.root
    }

    private fun openBottomSheet() {
        var bottomSheetFragment = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val view = layoutInflater.inflate(R.layout.dialog_otp, null)
        bottomSheetFragment.setContentView(view)
        var btnNext: ImageView? = null
        var resendCode: TextView? = null
        var changeNumber: TextView? = null
        var OTPTimer: TextView? = null
        btnNext = view.findViewById(R.id.btn_next)
        resendCode = view.findViewById(R.id.resendCode)
        OTPTimer = view.findViewById(R.id.OTPTimer)
        resendCode.setOnClickListener {
            val timer = object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var milis: Long = millisUntilFinished / 1000
                    if (milis.toString().isEmpty()) {
                        OTPTimer.visibility = View.GONE
                    } else {
                        OTPTimer.visibility=View.VISIBLE
                        OTPTimer.text = milis.toString()
                    }
                }
                override fun onFinish() {
                    sendVerificationCode(completeNumber.toString())
                }
            }
            timer.start()
        }
        btnNext!!.setOnClickListener {

            bottomSheetFragment.dismiss()
            callNewFragment()
        }
        bottomSheetFragment.show()

    }


    private fun callNewFragment() {
        val mainFragment = MainFragment()
        var transaction: FragmentTransaction =
            activity!!.supportFragmentManager!!.beginTransaction()
        transaction!!.replace(R.id.container, mainFragment)
        transaction!!.addToBackStack(null)
        transaction!!.commit()
    }

    private fun signInWithPhone(credential: PhoneAuthCredential) {
        mFirebaseMobileAuth.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login SuccessFull", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Incorrect OTP", Toast.LENGTH_SHORT).show()
                }
            })

    }

    /************************************************* GOOGLE AUTH START  ***********************************/
    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, mGoogleSignInOptions) }!!
    }

    private fun setupUI() {
        binding.llGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credentialGoogle = GoogleAuthProvider.getCredential(account!!.idToken, null)
        mFirebaseGoogleAuth.signInWithCredential(credentialGoogle).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Google sign Successful:(", Toast.LENGTH_LONG).show()
                callNewFragment()
            } else {
                Toast.makeText(context, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(context, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }


    /************************************************* GOOGLE AUTH END  ***********************************/
    private fun sendVerificationCode(completeNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            completeNumber,
            60,
            TimeUnit.SECONDS,
            requireActivity(),
            verificationCallbacks
        )

    }

    /** hide Toolbar from Existing Specific Fragment  **/
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}



