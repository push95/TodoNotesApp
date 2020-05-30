package com.example.todonotesapp.fragment.notesFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.todonotesapp.R
import com.example.todonotesapp.activity.MainActivity
import com.example.todonotesapp.model.Notes
import com.example.todonotesapp.viewmodel.MainActivityViewModel

class AddNotesFragment : Fragment(), TextWatcher{

    var rootView: View? = null
    private var title: EditText? = null
    private var description: EditText? = null
    private var mSaveBT: Button? = null
    private var mArrowSelectedBT: Button? = null
    private var mArrowUnSelectBT: Button? = null
    var newNotesViewModel: MainActivityViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_add_notes, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newNotesViewModel = (requireActivity() as MainActivity).newNotesViewModel
        getAllIDS(view)
        mArrowSelectedBT!!.setOnClickListener {
            val newNotes = Notes(title!!.text.toString(), description!!.text.toString())
            val noteslist = newNotesViewModel!!.notesListViewModel.value
            if (noteslist != null) {
                newNotesViewModel!!.notesListViewModel.value!!.add(newNotes)

            } else {
                val mutableList = ArrayList<Notes>()
                mutableList.add(newNotes)
                newNotesViewModel!!.notesListViewModel.value = mutableList
            }
            Toast.makeText(context, "Notes add Succesfully", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()

        }
    }
   private fun validation(){

        val newNotes = Notes(title!!.text.toString(), description!!.text.toString())
        val noteslist = newNotesViewModel!!.notesListViewModel.value
        if (noteslist != null) {
            newNotesViewModel!!.notesListViewModel.value!!.add(newNotes)

        } else {
            val mutableList = ArrayList<Notes>()
            mutableList.add(newNotes)
            newNotesViewModel!!.notesListViewModel.value = mutableList
        }
        Toast.makeText(context, "Notes add Succesfully", Toast.LENGTH_SHORT).show()
        /*requireActivity().supportFragmentManager.popBackStack()*/
    }


    private fun validate(): Boolean {
        if (title!!.text.isEmpty()) {
            title!!.requestFocus()
            title!!.error = "Enter your Title"
            return true
        } else if (description!!.text.isEmpty()) {
            description!!.requestFocus()
            description!!.error = "Enter your Description"
            return true
        }
        return false
    }

    private fun getAllIDS(view: View?) {
        title = view!!.findViewById(R.id.textView)
        description = view!!.findViewById(R.id.tv_title)
      //  mSaveBT = view!!.findViewById(R.id.button2)
        mArrowUnSelectBT=view.findViewById(R.id.btn_arrow_unselect)
        mArrowSelectedBT=view.findViewById(R.id.btn_arrow_select)
    }

    override fun afterTextChanged(s: Editable?) {
        val mUserTitle = title?.text.toString().trim()
        val mUserDescription = description?.text.toString().trim()

        if (mUserTitle.isNotEmpty() && mUserDescription.isNotEmpty()) {
            mArrowSelectedBT!!.visibility=View.VISIBLE
            mArrowUnSelectBT!!.visibility=View.GONE
        } else {
            mArrowUnSelectBT!!.visibility=View.VISIBLE
            mArrowSelectedBT!!.visibility=View.GONE
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
       /* val mUserTitle = title?.text.toString().trim()
        val mUserDescription = description?.text.toString().trim()

        if (mUserTitle.isNotEmpty() && mUserDescription.isNotEmpty()) {
            mArrowSelectedBT!!.visibility=View.VISIBLE
            mArrowUnSelectBT!!.visibility=View.GONE
        } else {
            mArrowUnSelectBT!!.visibility=View.VISIBLE
            mArrowSelectedBT!!.visibility=View.GONE
        }*/
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
       /* val mUserTitle = title?.text.toString().trim()
        val mUserDescription = description?.text.toString().trim()

        if (mUserTitle.isNotEmpty() && mUserDescription.isNotEmpty()) {
            mArrowSelectedBT!!.visibility=View.VISIBLE
            mArrowUnSelectBT!!.visibility=View.GONE
        } else {
            mArrowUnSelectBT!!.visibility=View.VISIBLE
            mArrowSelectedBT!!.visibility=View.GONE
        }
*/
    }


}

