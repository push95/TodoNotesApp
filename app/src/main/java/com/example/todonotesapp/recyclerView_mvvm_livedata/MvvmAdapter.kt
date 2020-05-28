package com.example.todonotesapp.recyclerView_mvvm_livedata

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.R


class MvvmAdapter(
    context: RecyclerViewMvvmActivity?,
    userArrayList: ArrayList<MvvmModel>
) : RecyclerView.Adapter<MvvmAdapter.MvvmViewHolder>() {

    var context: Activity? = null
    var mvvmModelList: ArrayList<MvvmModel>? = null
    var rootView :View? = null


    class MvvmViewHolder(view : View?) :RecyclerView.ViewHolder(view!!) {
       var  txtView_title = itemView.findViewById<TextView>(R.id.title2)
       var txtView_description = itemView.findViewById<TextView>(R.id.tv_title);



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MvvmViewHolder {
       rootView = LayoutInflater.from(context).inflate(R.layout.item_list_mvvm,parent,false);
        return  MvvmViewHolder(rootView);
    }

    override fun getItemCount(): Int {
        return mvvmModelList!!.size
    }

    override fun onBindViewHolder(holder: MvvmViewHolder, position: Int) {
        val user: MvvmModel = mvvmModelList!!.get(position)
        val viewHolder: MvvmViewHolder = holder

        viewHolder.txtView_title.text=user.title
        viewHolder.txtView_description.text=user.description
    }
}