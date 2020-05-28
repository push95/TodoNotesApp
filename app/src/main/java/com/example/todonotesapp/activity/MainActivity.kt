package com.example.todonotesapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.todonotesapp.R
import com.example.todonotesapp.fragment.MainFragment
import com.example.todonotesapp.viewmodel.AddListDataViewModel
import com.example.todonotesapp.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    var manager: FragmentManager? = null
    var transaction: FragmentTransaction? = null
    var newNotesViewModel :MainActivityViewModel?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newNotesViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        // adding fragment
        val mainFragment = MainFragment()
        manager = supportFragmentManager
        transaction = manager!!.beginTransaction()
        transaction!!.replace(R.id.container, mainFragment)
        transaction!!.addToBackStack(null)
        transaction!!.commit()


    }

}




