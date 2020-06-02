package com.example.todonotesapp.activity.anotherExample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.todonotesapp.R
import com.example.todonotesapp.activity.MainActivity
import com.example.todonotesapp.databinding.ActivityMain2Binding

import com.google.firebase.auth.FirebaseAuth

class Main2Activity : AppCompatActivity() {
    /*lateinit var binding: ActivityMain2Binding*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        setupUI()

        }

    private fun setupUI() {
      /* binding.signOutButton.setOnClickListener {
            logout()
        }*/

}

    private fun logout() {
        
        FirebaseAuth.getInstance().signOut();
    }
}
