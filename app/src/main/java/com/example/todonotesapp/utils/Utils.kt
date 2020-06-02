package com.example.todonotesapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Pushpendra 30/05/2020
 */

class Utils {
    /** HIDE KEYBOARD **/
    companion object{
        fun hideKeyboard(context: Context?, view: View){
            val mHideKey :InputMethodManager= context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mHideKey.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

      /** SHOW KEYBOARD **/
        fun showKeyboard(context: Context? ,view: View){
          val mShowKey: InputMethodManager =context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
          mShowKey.toggleSoftInputFromWindow(view.applicationWindowToken ,InputMethodManager.SHOW_FORCED ,0)
          view.requestFocus()
      }
    }

}