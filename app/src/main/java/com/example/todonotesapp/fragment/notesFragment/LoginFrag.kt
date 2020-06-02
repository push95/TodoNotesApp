package com.example.todonotesapp.fragment.notesFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.todonotesapp.R
import com.example.todonotesapp.databinding.FragmentLoginBinding
import kotlin.math.log


class LoginFrag : Fragment()  {
    var rootView :View?=null
    lateinit var loginBinding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /*loginBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        rootView= loginBinding.root
        //loginBinding.login=this
        return rootView*/
        loginBinding =FragmentLoginBinding.inflate(layoutInflater)
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBinding.btnFb.setOnClickListener{
            val mainFragment =MainFragment()
            var transaction = activity!!.supportFragmentManager!!.beginTransaction()
            transaction!!.replace(R.id.container, mainFragment)
            transaction!!.addToBackStack(null)
            transaction!!.commit()
        }
    }
}
