package com.example.todonotesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.R
import com.example.todonotesapp.model.Notes

class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.MyNotesViewHolder>() {

    private var notesList = ArrayList<Notes>()
    var context: Context?=null

    // Start with first item selected
    private var focusedItem = 0



 inner class MyNotesViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
     val listTitle = itemView.findViewById<TextView>(R.id.titleTV)
     val listDescription = itemView.findViewById<TextView>(R.id.descTV)
     init {
         itemView.setOnClickListener{
             // Redraw the old selection and the new
             notifyItemChanged(focusedItem);
             focusedItem = layoutPosition;
             notifyItemChanged(focusedItem);
         }
     }

     /*init {
         itemView.setOnClickListener{
             val selectedItem =notesList as ArrayList<Notes>
             for (list in selectedItem.indices){
                 selectedItem[list].isSelected = false

             }
             selectedItem[adapterPosition].isSelected = true
             notifyItemChanged(focusedItem)
             context?.let { it1 -> ContextCompat.getColor(it1, R.color.colorFab) }?.let { it2 ->
                 itemView.setBackgroundColor(it2)
             }
         }


     }*/
 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNotesViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_notes, parent, false)
        return MyNotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: MyNotesViewHolder, position: Int) {
        holder.listTitle.text = notesList[position].title
        holder.listDescription.text = notesList[position].description
        holder.itemView.isSelected = focusedItem ==position

        /*if (notesList?.get(position) is Notes) {
            val dataItem = notesList?.get(position) as Notes
            if (dataItem.isSelected) {
                context?.let { ContextCompat.getColor(it, R.color.colorFab) }
                    ?.let { holder.itemView.setBackgroundColor(it) }

            } else {
                context?.let { ContextCompat.getColor(it, R.color.colorFab) }
                    ?.let { holder.itemView.setBackgroundColor(it) }

            }
        }*/

    }

    fun update(modelList: ArrayList<Notes>) {
        notesList = modelList
        notifyDataSetChanged()
    }

    // item selected or not selected

}