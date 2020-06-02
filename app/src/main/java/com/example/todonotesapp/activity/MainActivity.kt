package com.example.todonotesapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.todonotesapp.R
import com.example.todonotesapp.fragment.sociallogin.SocialLoginFragment
import com.example.todonotesapp.utils.IOnBackPressed
import com.example.todonotesapp.viewmodel.MainActivityViewModel
import com.facebook.login.widget.LoginButton

class MainActivity : AppCompatActivity() {

    var manager: FragmentManager? = null
    var transaction: FragmentTransaction? = null
    var newNotesViewModel: MainActivityViewModel? = null
    var toolbar : Toolbar? =null

    // lateinit var callbackManager: CallbackManager
    var mLoginFbRL: RelativeLayout? = null
    var mLoginGoogleRL: RelativeLayout? = null
    var loginButton: LoginButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        newNotesViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        val socialLoginFragment = SocialLoginFragment()
        manager = supportFragmentManager
        transaction = manager!!.beginTransaction()
        transaction!!.replace(R.id.container, socialLoginFragment)
        transaction!!.addToBackStack(null)
        transaction!!.commit()
    }

    override fun onBackPressed() {
        val fragment =this.supportFragmentManager.findFragmentById(R.id.container)
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /** INFLATE THE MENU  THIS ADDS ITEMS TO ACTION BAR IF IT IS PRESENT **/
        menuInflater.inflate(R.menu.menu_main ,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar clicks here.the action bar will
        //automatically handle clicks on the home/up button, so long
        //as  you specify a parent activity in AndroidMAnifest.xml

        var item_id: Int =item.itemId

        if(item_id == R.id.signout){
            this.recreate()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}




