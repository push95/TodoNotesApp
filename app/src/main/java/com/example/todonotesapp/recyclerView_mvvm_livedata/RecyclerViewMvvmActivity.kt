package com.example.todonotesapp.recyclerView_mvvm_livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.R


class RecyclerViewMvvmActivity : AppCompatActivity() {

    var context: RecyclerViewMvvmActivity? = null
    var mvvmViewModel: MvvmViewModel? = null
    var recyclerView: RecyclerView? = null
    var mvvmAdapter: MvvmAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_mvvm)
        recyclerView = findViewById(R.id.rv_mvvm)

        mvvmViewModel = ViewModelProviders.of(this).get(MvvmViewModel::class.java)
        mvvmViewModel!!.getUserMutableLiveData()!!.observe(this!!, userListUpdateObserver)
    }
    private var userListUpdateObserver: Observer<ArrayList<MvvmModel>> =
        Observer<ArrayList<MvvmModel>> { userArrayList ->
            mvvmAdapter = MvvmAdapter(this, userArrayList)
            recyclerView!!.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            recyclerView!!.adapter = mvvmAdapter

        }

}
