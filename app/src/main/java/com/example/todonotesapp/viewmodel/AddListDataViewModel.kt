package com.example.todonotesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todonotesapp.model.Notes

class AddListDataViewModel :ViewModel() {
    var notesListViewModelItems: ArrayList<Notes> = ArrayList()

    fun updateItem(position: Int, updatedItem: Notes) {
        notesListViewModelItems[position] = updatedItem
    }
}