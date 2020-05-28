package com.example.todonotesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todonotesapp.model.Notes

class MainActivityViewModel : ViewModel() {

    val notesListViewModel = MutableLiveData<MutableList<Notes>>()

}