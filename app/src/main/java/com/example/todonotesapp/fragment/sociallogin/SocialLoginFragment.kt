package com.example.todonotesapp.fragment.sociallogin

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.todonotesapp.MvvmNewsRetrofit.repository.NewsRepos.getInstance
import com.example.todonotesapp.R
import com.example.todonotesapp.databinding.FragmentSocialLoginBinding
import com.example.todonotesapp.fragment.notesFragment.MainFragment
import com.example.todonotesapp.office_api_mvvm.model.login.request.LoginUserRequest
import com.example.todonotesapp.office_api_mvvm.model.login.response.UserLoginMainResponse
import com.example.todonotesapp.office_api_mvvm.model.socialEntry.request.response.SocialEntryResponseMain
import com.example.todonotesapp.office_api_mvvm.network.APIClient
import com.example.todonotesapp.office_api_mvvm.network.RetrofitClientApi
import com.example.todonotesapp.office_api_mvvm.view.LoginFrag
import com.facebook.*
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
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL
import java.util.concurrent.TimeUnit

class SocialLoginFragment : Fragment() {
    /** GOOGLE SIGNIN **/
    private val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var mFirebaseGoogleAuth: FirebaseAuth
    var telephonyManager: TelephonyManager? = null
    private var progressBar :ProgressBar?=null
    val loginUserRequest=LoginUserRequest()


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


    //api call split
    var phoneNo: String? = null
    var mobile:String?=null
    var withOutCode:String?=null
    var ext:String = ""
    var phoneN:String? = ""



    /**  Binding **/
    lateinit var binding: FragmentSocialLoginBinding


    // fb
    lateinit var callbackManager: CallbackManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_social_login, container, false)
        callbackManager = CallbackManager.Factory.create()
        mFirebaseMobileAuth = FirebaseAuth.getInstance()
        mFirebaseGoogleAuth = FirebaseAuth.getInstance()
        binding.ccp!!.registerCarrierNumberEditText(binding.etMobile)
        binding.progress!!.isIndeterminate=true
        loginUserRequest.phone_no=completeNumber
      //  phone_no=completeNumber as LoginUserRequest

        // show it

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
                override fun onSuccess(loginResult: LoginResult?) {
                    Toast.makeText(context, "Login Success", Toast.LENGTH_LONG).show()
                    Log.i("Response", loginResult.toString());
                    binding.progress!!.visibility = View.VISIBLE
                    loginResult?.accessToken?.let { getUserProfile(it) }

                }
                override fun onCancel() {
                    Toast.makeText(context, "Login Cancelled", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException?) {
                    Toast.makeText(context, exception!!.message, Toast.LENGTH_LONG).show()

                }
            })
        binding.etMobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                 completeNumber = binding.ccp!!.fullNumberWithPlus
                //phoneNo = binding.ccp!!.fullNumberWithPlus as LoginUserRequest
                if (completeNumber.toString().isNotEmpty() && completeNumber.toString().length < 12
                ) {
                    binding.etMobile.error = "failed"
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                completeNumber = binding.ccp!!.fullNumberWithPlus
               // completeNumber = binding.ccp!!.fullNumberWithPlus as LoginUserRequest
                if (completeNumber.toString().isNotEmpty() && completeNumber.toString().length > 12
                ) {
                    sendVerificationCode(completeNumber.toString())
                    //openBottomSheet(isOTPData.toString(), completeNumber)
                }
            }

        })

        verificationCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                //Toast.makeText(context, "Verification Completed", Toast.LENGTH_SHORT).show()
                Log.d("verified", "onVerificationCompleted$phoneAuthCredential")
                var code: String = phoneAuthCredential.smsCode.toString()
                if (code != null) {
                    val credential = PhoneAuthProvider.getCredential(mOTP.toString(), code)
                    signInWithPhone(credential)

                }
                //signInWithPhone(phoneAuthCredential);
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


              //  callLoginApi(loginUserRequest)
            }
        }
        return binding.root
    }
    private fun callLoginApi(phoneN: String?) {
        val loginUserRequest=LoginUserRequest()
        loginUserRequest.phone_no=phoneN
        val call: Call<UserLoginMainResponse>? = loginUserRequest?.let {
            APIClient.getRetrofitClientInterface.getUserMobile(loginUserRequest)
        }
        call!!.enqueue(object : Callback<UserLoginMainResponse> {
            override fun onResponse(call: Call<UserLoginMainResponse>, response: Response<UserLoginMainResponse>
            ) {
                Log.d("Call Login APi", "onResponse$response")
                if (response != null && response.isSuccessful) {
                       // Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    try {
                        val responseData = response?.body()
                        if (responseData?.status ==true) {
                            val socialFragment = SocialFragment()
                            val args = Bundle()
                            args.putSerializable("response", responseData)
                            socialFragment.arguments = args
                            var transaction: FragmentTransaction =
                                activity!!.supportFragmentManager!!.beginTransaction()
                            transaction!!.replace(R.id.container, socialFragment)
                            transaction!!.addToBackStack(null)
                            transaction!!.commit()
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                    }

                }
            }
            override fun onFailure(call: Call<UserLoginMainResponse>, t: Throwable) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()}
        })
    }


    private fun getUserProfile(currentAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            currentAccessToken
        ) { `object`, response ->
            try {
                val id = `object`.getString("id")
                Log.i("id", id)
                val name = `object`.getString("name")
                Log.i("name", name)
                val email = `object`.getString("email")
                Log.i("email", email)
                val profilePic = URL("https://graph.facebook.com/$id/picture?")
                val image = profilePic.toString()
                Log.i("profile_pic", image + "")
                callSocialEntryApi(id, name, email, image)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id, name, email")
        request.parameters = parameters
        request.executeAsync()

    }

    private fun callSocialEntryApi(id: String, name: String, email: String, image: String) {
        val call: Call<SocialEntryResponseMain> =
            RetrofitClientApi.getRetrofitClientInterface.getUserResult(
                name, email, image, "mdmfnniidndndn", "Android")
        call.enqueue(object : Callback<SocialEntryResponseMain> {
            override fun onResponse(call: Call<SocialEntryResponseMain>, response: Response<SocialEntryResponseMain>) {
                Log.d("Call Social Entry", "onResponse$response")
                if (response != null && response.isSuccessful) {
                   binding.progress!!.visibility = View.GONE
                    try {
                        val responseData = response?.body()
                        if (responseData?.status ==true) {
                            val loginFrag = LoginFrag()
                            val args = Bundle()
                            args.putSerializable("response", responseData!!.result)
                            loginFrag.arguments = args
                            var transaction: FragmentTransaction =
                                activity!!.supportFragmentManager!!.beginTransaction()
                            transaction!!.replace(R.id.container, loginFrag)
                            transaction!!.addToBackStack(null)
                            transaction!!.commit()
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                    }
                }
            }

            override fun onFailure(call: Call<SocialEntryResponseMain>, t: Throwable) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun openBottomSheet(mOTP: String, completeNumber: String?) {
        var bottomSheetFragment = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val view = layoutInflater.inflate(R.layout.dialog_otp, null)
        bottomSheetFragment.setContentView(view)
        var number: String? = null
        number = completeNumber
        var btnNext: ImageView? = null
        var resendCode: TextView? = null
        var mMobileNumberTV: TextView? = null
        var mOTPVerifyET: EditText? = null
        var changeNumber: TextView? = null
        var OTPTimer: TextView? = null
        btnNext = view.findViewById(R.id.btn_next)
        resendCode = view.findViewById(R.id.resendCode)
        OTPTimer = view.findViewById(R.id.OTPTimer)
        mOTPVerifyET = view.findViewById(R.id.et_verifyOtp_dialog)
        mMobileNumberTV = view.findViewById(R.id.tv_mobile_number)
        mMobileNumberTV.text = number
        if (mOTP != null) {
            bottomSheetFragment.dismiss()

        }
        isOTPData = mOTPVerifyET.text.toString()
        resendCode.setOnClickListener {
            val timer = object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var milis: Long = millisUntilFinished / 1000
                    if (milis.toString().isEmpty()) {
                        OTPTimer.visibility = View.GONE
                    } else {
                        OTPTimer.visibility = View.VISIBLE
                        OTPTimer.text = milis.toString()
                    }
                }

                override fun onFinish() {
                    sendVerificationCode(this@SocialLoginFragment.completeNumber.toString())
                }
            }
            timer.start()
        }
        btnNext!!.setOnClickListener {
            callNewFragment()
            /*val credential  =PhoneAuthProvider.getCredential(mOTP, isOTPData!!)
            signInWithPhone(credential)*/

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
                    /*  Toast.makeText(context, "Login OTP SuccessFull", Toast.LENGTH_SHORT).show()*/
                    if (completeNumber!!.startsWith("+") || completeNumber!!.length ==10){
                        ext= completeNumber!!.substring(0,3)
                        phoneN=completeNumber!!.substring(3)
                    }else{
                        ext = "";
                        phoneN = completeNumber;
                    }
                    callLoginApi(phoneN)
                } else {
                    /*Toast.makeText(context, "Incorrect OTP", Toast.LENGTH_SHORT).show()*/
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
                /* Toast.makeText(context, "Google sign Successful:(", Toast.LENGTH_LONG).show()*/

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

    fun callLoginFragment() {
        val mainFragment = LoginFrag()
        var transaction: FragmentTransaction =
            activity!!.supportFragmentManager!!.beginTransaction()
        transaction!!.replace(R.id.container, mainFragment)
        transaction!!.addToBackStack(null)
        transaction!!.commit()
    }
}



