package com.example.todonotesapp.fragment.notesFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.todonotesapp.R
import com.example.todonotesapp.activity.MainActivity
import com.example.todonotesapp.databinding.FragmentAddNotesBinding
import com.example.todonotesapp.model.Notes
import com.example.todonotesapp.utils.Utils
import com.example.todonotesapp.viewmodel.MainActivityViewModel
import com.google.android.material.textfield.TextInputEditText

class AddNotesFragment : Fragment() {

    var newNotesViewModel: MainActivityViewModel? = null
    lateinit var binding: FragmentAddNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /** BINDING  **/
        binding=DataBindingUtil.inflate(inflater ,R.layout.fragment_add_notes,container,false)
        //binding = FragmentAddNotesBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newNotesViewModel = (requireActivity() as MainActivity).newNotesViewModel
        binding.save.setOnClickListener {
            Utils.hideKeyboard(context, view)
            if (!validate()) {
                val newNotes =
                    Notes(binding.tvTitle!!.text.toString(), binding.tvDesc!!.text.toString())
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

    }

    private fun validate(): Boolean {
        if (binding.tvTitle!!.text!!.isEmpty()) {
            binding.tvTitle!!.requestFocus()
            binding.tvTitle!!.error = "Enter your Title"
            return true
        } else if (binding.tvDesc!!.text!!.isEmpty()) {
            binding.tvDesc!!.requestFocus()
            binding.tvDesc!!.error = "Enter your Description"
            return true
        }
        return false
    }
}

