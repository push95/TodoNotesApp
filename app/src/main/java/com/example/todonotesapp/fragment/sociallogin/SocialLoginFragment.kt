package com.example.todonotesapp.fragment.sociallogin


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.todonotesapp.R
import com.example.todonotesapp.activity.anotherExample.Main2Activity
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
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks



class SocialLoginFragment : Fragment() {
    /** GOOGLE SIGNIN **/
    private val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var mFirebaseGoogleAuth: FirebaseAuth


    /**  Binding **/
    lateinit var binding: FragmentSocialLoginBinding

    //firebase
    private val FireSIGN_IN = 1001
    // fb
    lateinit var callbackManager: CallbackManager

    lateinit var mFirebaseAuth: FirebaseAuth

    var etPhone: EditText? = null
    var mVerificationId: String? = null
    var etOtp:EditText? = null
    var mSendOtpTV: Button? = null
    var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSocialLoginBinding.inflate(layoutInflater)
        callbackManager = CallbackManager.Factory.create()
        mFirebaseAuth=FirebaseAuth.getInstance()
        mFirebaseGoogleAuth=FirebaseAuth.getInstance()

        // for google
        configureGoogleSignIn()
        setupUI()


       // firebaseAuth(mFirebaseAuth)

        /** BINDING **/

        binding.btnFb!!.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("public_profile", "email"))
        }
        // callback Registration
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(result: LoginResult?) {
                    val mainFragment = MainFragment()
                    var transaction: FragmentTransaction =
                        activity!!.supportFragmentManager!!.beginTransaction()
                    transaction!!.replace(R.id.container, mainFragment)
                    transaction!!.addToBackStack(null)
                    transaction!!.commit()
                }

                override fun onCancel() {
                    Toast.makeText(context, "Login Cancelled", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException?) {
                    Toast.makeText(context, exception!!.message, Toast.LENGTH_LONG).show()

                }
            })

            firebaseCallback()
        return binding.root
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
        binding.googleButton.setOnClickListener {
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
                val mainFragment = MainFragment()
                var transaction: FragmentTransaction =
                    activity!!.supportFragmentManager!!.beginTransaction()
                transaction!!.replace(R.id.container, mainFragment)
                transaction!!.addToBackStack(null)
                transaction!!.commit()
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


    private fun firebaseCallback() {
        mCallbacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential : PhoneAuthCredential) {
               // val code: String = phoneAuthCredential.smsCode.toString()
                Toast.makeText(context, "Verification Complete", Toast.LENGTH_SHORT).show();

            }

            override fun onVerificationFailed(exception: FirebaseException) {
                Toast.makeText(context, "Verification Failed", Toast.LENGTH_SHORT).show();
            }

            override fun onCodeSent(verficationId: String, token: ForceResendingToken) {
               // super.onCodeSent(verficationId, token)
                Toast.makeText(context, "Code Sent", Toast.LENGTH_SHORT).show();
                mVerificationId=verficationId

            }

        }


    }

  /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account =task.getResult(ApiException::class.java)
                    fireBaseAuthWithGoogle(account)
                }catch (e :ApiException){
                    Toast.makeText(context, "Google SignIn Failed :(",Toast.LENGTH_SHORT).show()
                }
        }


    }*/

   /* private fun fireBaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val googleCredential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        mFirebaseGoogleAuth.signInWithCredential(googleCredential).addOnCompleteListener{
            if (it.isSuccessful){
                val nextIntent =Intent(context, Main2Activity::class.java)
                startActivity(nextIntent)
            }else {
                Toast.makeText(context, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }

    }*/

    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>){
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID", googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)

            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)

        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }

    }


   
}



