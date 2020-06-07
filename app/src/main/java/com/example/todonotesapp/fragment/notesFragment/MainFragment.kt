package com.example.todonotesapp.fragment.notesFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.model.Notes
import com.example.todonotesapp.R
import com.example.todonotesapp.activity.MainActivity
import com.example.todonotesapp.adapter.NotesAdapter
import com.example.todonotesapp.databinding.FragmentMainBinding
import com.example.todonotesapp.fragment.notesFragment.AddNotesFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainFragment : Fragment() {
    var rootView: View? = null
    private var addNotesFAB: FloatingActionButton? = null
    private var recyclerView: RecyclerView? = null
    lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** DATA BINDING **/
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        //binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.rv_main)
        val linearLayoutManager = LinearLayoutManager(context)
        val notesAdapter = NotesAdapter()
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = notesAdapter
        val mNotesViewModel = (requireActivity() as MainActivity).newNotesViewModel
        mNotesViewModel!!.notesListViewModel.observe(viewLifecycleOwner, Observer {
            notesAdapter!!.update(it as ArrayList<Notes>)
        })
        binding.fab.setOnClickListener {
            val addNotesFragment = AddNotesFragment()
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.container, addNotesFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


    }


}
